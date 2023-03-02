package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.Container;

<<<<<<< HEAD

/**
 * @author sn
 */
=======
import java.util.List;
import java.util.Map;

>>>>>>> 4aba4c5c63cc975568f6c1a10ed7576a9a7b6bb9
public interface ContainerService extends IService<Container> {
    //获取容器
    List<Container> getContainersByStatus(Integer containerId, List<String> status);
}
