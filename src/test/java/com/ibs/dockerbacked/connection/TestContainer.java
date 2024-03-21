package com.ibs.dockerbacked.connection;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.command.ExecCreateCmdImpl;
import com.github.dockerjava.core.command.LogContainerCmdImpl;
import com.github.dockerjava.core.exec.ExecCreateCmdExec;
import com.ibs.dockerbacked.util.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@SpringBootTest
public class TestContainer {
//    @Autowired
    private DockerConnection dockerConnection;
//    @Autowired
    private ContainerModel containerModel;

    private String containerId = "f44ba69f3c46";

    @Before
    public void init(){
//        dockerConnection = new DockerConnection("dockerxylyjy","docker@123789","xylyjy@gmail.com","unix:///var/run/docker.sock","https://index.docker.io/v1/");
        dockerConnection = new DockerConnection("dockerxylyjy","docker@123789","xylyjy@gmail.com","tcp://localhost:2375","https://index.docker.io/v1/");



        containerModel = new ContainerModel(dockerConnection.connect());
//        dockerConnection.getDockerClient().copyFileFromContainerCmd()
    }


    @Test
    public void getInfo(){
        EntityUtils.print(containerModel.inspectContainer("57eae25089a1"));
    }

    @Test
    public void create(){

        List list = new ArrayList<>();
        list.add(new PortBinding(new Ports.Binding("0.0.0.0","3306"),new ExposedPort(3306)));

        List<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Qq123456789");

        CreateContainerResponse createContainerResponse = containerModel.createContainer(2,1024,10,100,"test","mysql:latest",list,envs);
        containerId = createContainerResponse.getId();
        log.info(createContainerResponse.toString());
    }

    @Test
    public void start(){
        containerModel.startContainer("f26823a5ee61");
    }

    @Test
    public void stop(){
        containerModel.stopContainer("889c0d99121b43618df8e5f711afa9e10cc17d7627e82f8aaf05b700dae34f8c");
    }

    @Test
    public void rm(){
        containerModel.deleteContaqqiner("889c0d99121b43618df8e5f711afa9e10cc17d7627e82f8aaf05b700dae34f8c");
    }

    /**
     * 获取容器执行指令后的信息
     */
    @Test
    public void exec(){
        DockerClient dockerClient = dockerConnection.connect();

        ExecCreateCmdResponse execCreateCmd =  dockerClient.execCreateCmd("41ce0447dc53dd1da4430487386af5eff8d8455c5009041b084a0da35b3c0cd9")
                .withCmd("/bin/sh", "-c", "echo hello; echo 0 >> /tmp/test_result")
                .withAttachStderr(true)
                .withAttachStdout(true)
                .exec();
        var execCmdId = execCreateCmd.getId();
        dockerClient.execStartCmd(execCmdId).exec(new ResultCallback.Adapter<Frame>() {
            @Override
            public void onNext(Frame item) {
                log.info("Exec next: {}", item);
                super.onNext(item);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("Error occurred" + throwable);
                super.onError(throwable);
            }

            @Override
            public void onComplete() {
                log.info("Exec completed");
                super.onComplete();
            }
        });
    }

    @Test
    public void exec2(){
        String info = containerModel.execCommand("41ce0447dc53dd1da4430487386af5eff8d8455c5009041b084a0da35b3c0cd9","ls");
        System.out.println(info);
    }

    /**
     * 直接获取容器状态信息
     */
    @Test
    public void getLog(){
        DockerClient dockerClient = dockerConnection.connect();
        LogContainerCmd logContainerCmd = dockerClient.logContainerCmd(containerId)
                .withTail(100)
                .withTimestamps(true)
                .withStdOut(true)
                .withStdErr(false);
        List<String> logs = new ArrayList<>();
        try {
            logContainerCmd.exec(new ResultCallback.Adapter<>() {
                @Override
                public void onNext(Frame object) {
                    logs.add(object.toString());
                }
            }).awaitCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Size of logs: " + logs.size());
        for (String log: logs) {
            System.out.println("Logging log: " + log);
        }

    }

    @Test
    public void status(){
        DockerClient dockerClient = dockerConnection.connect();
        InspectContainerResponse config =  dockerClient.inspectContainerCmd(containerId).exec();
        config.getState().getStatus();
        System.out.println(config.getState().getStatus());
    }

    /**
     * used_memory = memory_stats.usage - memory_stats.stats.cache
     * available_memory = memory_stats.limit
     * Memory usage % = (used_memory / available_memory) * 100.0
     * cpu_delta = cpu_stats.cpu_usage.total_usage - precpu_stats.cpu_usage.total_usage
     * system_cpu_delta = cpu_stats.system_cpu_usage - precpu_stats.system_cpu_usage
     * number_cpus = lenght(cpu_stats.cpu_usage.percpu_usage) or cpu_stats.online_cpus
     * CPU usage % = (cpu_delta / system_cpu_delta) * number_cpus * 100.0
      * @throws InterruptedException
     */
    @Test
    public void stats() throws InterruptedException {
        DockerClient dockerClient = dockerConnection.connect();
        ResultCallback<Statistics>result = dockerClient.statsCmd("08447b73049a")
                .withContainerId("f44ba69f3c46")
                .exec(new ResultCallback.Adapter<>(){
            @Override
            public void onNext(Statistics object) {
                super.onNext(object);
                System.out.println("进程信息");
                if(object.getCpuStats().getSystemCpuUsage()!=null&&object.getPreCpuStats().getSystemCpuUsage()!=null) {
                    double cpuDelta = object.getCpuStats().getCpuUsage().getTotalUsage() - object.getPreCpuStats().getCpuUsage().getTotalUsage();
                    double cpuSysDetal = object.getCpuStats().getSystemCpuUsage() - object.getPreCpuStats().getSystemCpuUsage();
                    double cpuPortion = cpuDelta / cpuSysDetal * object.getCpuStats().getOnlineCpus() * 100f;

                }
                if(object.getMemoryStats().getUsage()!=null){
//                    double usedMemory = object.getMemoryStats().getUsage()-object.getMemoryStats().getStats().getCache();
                    double usedMemory = object.getMemoryStats().getUsage();
                    double availableMemory = object.getMemoryStats().getLimit();
                    double memoryPortion = usedMemory/availableMemory*100f;
                }


                String info = object.getPreCpuStats().toString()+object.getMemoryStats().toString();
                System.out.println(info);
            }
        }).awaitStarted();
        System.out.println(result.toString());
        while(true){
            Thread.sleep(1000);
            result.onComplete();
        }
    }
    @Test
    public void testCopy(){
        DockerClient dockerClient = dockerConnection.connect();
        dockerClient.copyArchiveToContainerCmd(containerId)
                .withHostResource("C:\\Users\\叶子\\Desktop\\springmvc\\dockerbaked\\pom.xml")
                .withRemotePath("/home")
                .exec();

    }


    @Test
    public void testDownloadFile() throws IOException {
        DockerClient dockerClient = dockerConnection.connect();
        byte[] bytes = dockerClient.copyArchiveFromContainerCmd(containerId,"/home/pom.xml")
                .exec().readAllBytes();
        File file = new File("C:\\Users\\叶子\\Desktop\\springmvc\\dockerbaked\\Zekk\\pom.xml");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
    }

    @Test
    public void File(){

    }



}
