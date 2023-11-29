package com.ibs.dockerbacked.task.event;

import com.github.dockerjava.api.model.PullResponseItem;

/**
 * 拉去镜像事件
 * @author supeng
 */
public class PullImageEvent extends Event<PullResponseItem> {

    {
        setName("pullImage");
    }

}
