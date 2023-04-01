package com.ibs.dockerbacked.entity.dto;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.ibs.dockerbacked.entity.Container;
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
    private List<String> Env;


//    //端口
//    private String hostPort;
//    //docker端口
//    private int exposedPort;

    private List<String> ports;

    //镜像名称
    private String ImageName;

    //工作目录
    private String WorkingDir;

    //是否关闭容器的网络
    private boolean NetworkDisabled;

    //容器信息资料
    private Container HostConfig;

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
