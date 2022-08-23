package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles(value = {"管理员"},logical = Logical.OR)
@RequestMapping("ibs/api/admin")
public class AdminControl {

    @GetMapping(value = "containers")
    public ResponseBody getContainers(){
        return null;
    }

}
