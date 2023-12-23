package com.ibs.dockerbacked.task.event;

import com.github.dockerjava.api.model.PullResponseItem;
import com.ibs.dockerbacked.entity.Image;

/**
 * 拉去镜像事件
 * @author supeng
 */
public class PullImageEvent extends Event<Image> {

    {
        setName("pullImage");
    }

}
