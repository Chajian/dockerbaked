package com.ibs.dockerbacked.task.event;

import com.github.dockerjava.api.model.PullResponseItem;
import com.ibs.dockerbacked.entity.Image;
import lombok.Data;

/**
 * 拉去镜像事件
 * @author supeng
 */
@Data
public class PullImageEvent extends Event<Image> {

    {
        setName("pullImage");
        setUpdateTimes(0);
    }
    /**拉取进度**/
    private int updateTimes;

}
