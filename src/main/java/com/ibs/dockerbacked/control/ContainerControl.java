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
import com.ibs.dockerbacked.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ResponseBody deleteContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable String id){
        String account = JwtUtils.getAccount(token);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",String.valueOf(userEntity.getId()));
        QueryWrapper queryWrapper = new QueryWrapper(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.deleteContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("删除成功!");
        }
        return ResponseBody.Fail("删除失败!");
    }
    @RequestMapping(value = "/{containerId}/start",method = RequestMethod.POST)
    public ResponseBody startContainer(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,@PathVariable("containerId") String id){
//        String account = JwtUtils.getAccount(token);
        Map<String,String> map =  new HashMap<>();
        map.put("id",id);
        map.put("user_id","1");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.allEq(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.startContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("开启成功!");
        }
        return ResponseBody.Fail("开启失败!");
    }

    @RequestMapping(value = "/{containerId}/restart",method = RequestMethod.POST)
    public ResponseBody restartContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable("containerId") String id){
        String account = JwtUtils.getAccount(token);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",String.valueOf(userEntity.getId()));
        QueryWrapper queryWrapper = new QueryWrapper(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.restartContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("重启成功!");
        }
        return ResponseBody.Fail("重启失败!");
    }

    @RequestMapping(value = "/{containerId}/stop",method = RequestMethod.POST)
    public ResponseBody stopContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String id){
        String account = JwtUtils.getAccount(token);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",String.valueOf(userEntity.getId()));
        QueryWrapper queryWrapper = new QueryWrapper(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.stopContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("关机成功!");
        }
        return ResponseBody.Fail("关机失败!");
    }
    @RequestMapping(value = "/{containerId}/pause",method = RequestMethod.POST)
    public ResponseBody pauseContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String id){
        String account = JwtUtils.getAccount(token);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",String.valueOf(userEntity.getId()));
        QueryWrapper queryWrapper = new QueryWrapper(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.pauseContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("暂停成功!");
        }
        return ResponseBody.Fail("暂停失败!");
    }
    @RequestMapping(value = "/{containerId}/rename",method = RequestMethod.POST)
    public ResponseBody renameContainer(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String id){
        String account = JwtUtils.getAccount(token);
        UserEntity userEntity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda().eq(UserEntity::getAccount,account));
        Map<String,String> map = new HashMap<>();
        map.put("id",id);
        map.put("user_id",String.valueOf(userEntity.getId()));
        QueryWrapper queryWrapper = new QueryWrapper(map);
        ContainerEntity containerEntity = containerMapper.selectOne(queryWrapper);
        if( containerEntity!=null){
            containerServer.renameContainer(containerEntity.getId());
            return ResponseBody.SUCCESS("重命名成功!");
        }
        return ResponseBody.Fail("重命名失败!");
    }


}
