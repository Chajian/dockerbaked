package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ibs/api/admin")
public class AdminControl {

    @GetMapping(value = "containers")
    public ResponseBody getContainers(){
        return null;
    }

}
