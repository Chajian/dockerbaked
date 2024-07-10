package com.ibs.dockerbacked.entity.dto;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Hardware;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @version 1.0
 * @descript 容器的新增信息
 * @date 2023/3/4 20:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "新增容器配置")
public class AddContainer {
    //环境
    @ApiModelProperty( value = "环境变量XXX=yy，例如MYSQL_ROOT_PASSWORD=123456")
    private List<String> envs;

    @ApiModelProperty( value = "端口映射HOSTPORT=CONTAINERPORT，例如80:90。把主机80端口映射到容器90端口")
    private List<String> ports;

    //镜像名称
    @ApiModelProperty( value = "镜像名称")
    private String imageName;

    //工作目录
    @ApiModelProperty( value = "工作目录")
    private String workingDir;

    //是否关闭容器的网络
    @ApiModelProperty( value = "容器的网络状态，true开启，false关闭")
    private boolean networkDisabled;

   //容器名字
    @ApiModelProperty( value = "容器名称")
    private String containerName;
    //容器资料 todo
    @ApiModelProperty( value = "容器硬件配置")
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
