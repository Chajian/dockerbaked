package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ibs/api/images")
public class ImageControl {

    @GetMapping(value = "")
    public ResponseBody getImages(){
        return null;
    }

    @PostMapping(value = "pull")
    public ResponseBody pullImage(){
        return null;
    }


}
