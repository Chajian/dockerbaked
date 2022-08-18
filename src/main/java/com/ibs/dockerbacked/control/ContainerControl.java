package com.ibs.dockerbacked.control;

import com.ibs.dockerbacked.data.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ibs/api/containers")
public class ContainerControl {

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseBody getContainers(boolean all,String filters){
        return null;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseBody createContainer(){
        return null;
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
