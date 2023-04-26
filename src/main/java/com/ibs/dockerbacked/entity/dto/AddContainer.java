package com.ibs.dockerbacked.entity.dto;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @version 1.0
 * @descript 容器的新增信息
 * @date 2023/3/4 20:34
 */
@Data
public class AddContainer {
    //环境
    private List<String> envs;


    private List<String> ports;

    //镜像名称
    private String imageName;

    //工作目录
    private String workingDir;

    //是否关闭容器的网络
    private boolean networkDisabled;

   //容器名字
    private String containerName;
    //容器资料 todo
    Hardware hardware;
    public List<PortBinding> generatePorts(){
        List<PortBinding> list = new ArrayList<>();
        if(ports.size()>0){
            for(String s:ports){
                String[] info = s.split(":");
                PortBinding portBinding = new PortBinding(new Ports.Binding("0.0.0.0",info[0]),new ExposedPort(Integer.valueOf(info[1])));
                list.add(portBinding);
            }
            return list;
        }
        return null;
    }
}
