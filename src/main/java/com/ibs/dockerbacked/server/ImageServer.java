package com.ibs.dockerbacked.server;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class ImageServer {

    DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost("npipe:////./pipe/docker_engine")
            .withDockerTlsVerify(false)
            .withRegistryUrl("https://index.docker.io/v1/")
            .withRegistryEmail("xylyjy@gmail.com")
            .withRegistryUsername("dockerxylyjy")
            .withRegistryPassword("docker@123789")
            .build();

    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    DockerClient dockerClient = DockerClientImpl.getInstance(config,httpClient);


    public void inspectImage(String imageName){
        log.info(dockerClient.inspectImageCmd(imageName).exec().toString());
    }


    public void pullImage(String imageName,String tag) throws InterruptedException {
        dockerClient.pullImageCmd(imageName)
                .withTag(tag)
                .start().awaitCompletion();
    }

    public List<SearchItem> searchImage(String imageName, int size){
        return dockerClient.searchImagesCmd(imageName).withLimit(size).exec();
    }

    public List<Image> getImages(String imageName){
        return dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();
    }

}
