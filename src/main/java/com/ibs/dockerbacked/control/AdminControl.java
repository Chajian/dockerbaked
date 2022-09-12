package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ContainerEntity;
import com.ibs.dockerbacked.data.ResponseBody;
import com.ibs.dockerbacked.mapper.ContainerMapper;
import com.ibs.dockerbacked.server.ContainerServer;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiresRoles(value = {"管理员"},logical = Logical.OR)
@RequestMapping("ibs/api/admin")
public class AdminControl {

    @Autowired
    ContainerServer containerServer;

    @Autowired
    ContainerMapper containerMapper;

    @GetMapping(value = "containers")
    public ResponseBody getContainers(@RequestParam("all")boolean all,@RequestParam(value = "filters",required = false)String filters){
        if(all){
            List<ContainerEntity> list = containerMapper.selectList(null);
            return ResponseBody.SUCCESS(list);
        }
        else{
            switch (filters){

            }
        }
        return null;


    }

}
