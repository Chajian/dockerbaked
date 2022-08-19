package com.ibs.dockerbacked.control;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.ibs.dockerbacked.data.ContainerEntity;
import com.ibs.dockerbacked.data.ResponseBody;
import com.ibs.dockerbacked.data.UserEntity;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.server.ContainerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ibs/api/containers")
public class ContainerControl {

    @Autowired
    ContainerMapper containerMapper;

    @Autowired
    ContainerServer containerServer;

    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseBody getContainers(@RequestParam(value = "all") boolean all, @RequestParam(value = "filters",required = false) String filters){
        //假设userId存在
        long userId = 1;
        List<ContainerEntity> containerEntityList = containerMapper.selectList(new QueryWrapper<ContainerEntity>().lambda().eq(ContainerEntity::getUserId,userId));
        if(all){
            return ResponseBody.SUCCESS(containerEntityList);
        }
        else{

        }

        return ResponseBody.Fail("请求出错");
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseBody createContainer(@RequestParam(value = "name") String name, @RequestParam(value = "imageName")String imageName, @RequestParam(value = "ports",required = false) Map<Integer,Integer> ports){
        long userId = 1;
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getId,userId));
        //余额是否足够
        boolean enough = true;
        if(enough){
            List<PortBinding> list = new ArrayList<>();
            if(ports!=null) {
                for (int key : ports.keySet()) {
                    PortBinding portBinding = new PortBinding(new Ports.Binding(null, String.valueOf(key)), new ExposedPort(ports.get(key)));
                    list.add(portBinding);
                }
            }
            containerServer.createContainer(name,imageName,list);
            return ResponseBody.SUCCESS("成功!");
        }
        else{
            return ResponseBody.Fail("余额不足");
        }
    }

    @RequestMapping(value = "/{containerId}",method = RequestMethod.GET)
    public ResponseBody deleteContainer(@PathVariable String id){
        return null;
    }
    @RequestMapping(value = "/{containerId}/start",method = RequestMethod.POST)
    public ResponseBody startContainer(@PathVariable String id){
        return null;
    }
    @RequestMapping(value = "/{containerId}/stop",method = RequestMethod.POST)
    public ResponseBody stopContainer(@PathVariable String id){
        return null;
    }
    @RequestMapping(value = "/{containerId}/restart",method = RequestMethod.POST)
    public ResponseBody restartContainer(@PathVariable String id){
        return null;
    }
    @RequestMapping(value = "/{containerId}/pause",method = RequestMethod.POST)
    public ResponseBody pauseContainer(@PathVariable String id){
        return null;
    }
    @RequestMapping(value = "/{containerId}/rename",method = RequestMethod.POST)
    public ResponseBody renameContainer(@PathVariable String id){
        return null;
    }


}
