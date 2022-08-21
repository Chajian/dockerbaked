package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresRoles(value = {"学生","管理员"},logical = Logical.OR)
@RequestMapping("ibs/api/images")
public class ImageControl {


    @GetMapping(value = "")
    public ResponseBody getImages(){
        return null;
    }

    @PostMapping(value = "pull")
    public ResponseBody pullImage(@RequestParam("name") String imageName, @RequestParam("tag") String tag){



        return null;
    }


}
