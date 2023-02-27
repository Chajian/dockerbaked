package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import com.ibs.dockerbacked.server.ImageServer;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresRoles(value = {"学生","管理员"},logical = Logical.OR)
@RequestMapping("ibs/api/images")
public class ImageControl {


    @Autowired
    ImageServer imageServer;

    @GetMapping(value = "")
    public ResponseBody getImages(){
        return null;
    }

    @PostMapping(value = "pull")
    public ResponseBody pullImage(@RequestParam("name") String imageName, @RequestParam("tag") String tag) throws InterruptedException {
        imageServer.pullImage(imageName,tag);
        return ResponseBody.SUCCESS("拉取成功!");
    }


}
