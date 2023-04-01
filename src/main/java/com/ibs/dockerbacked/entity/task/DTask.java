package com.ibs.dockerbacked.entity.task;

/**
 * 任务接口
 * @author Yanglin
 */
public interface DTask {

    /**
     * 任务运行函数
     */
    public void start();


    /**
     * 运行方法
     */
    public void run();

    /**
     * 回调函数
     */
    public void recall();

    /**
     * 任务死亡的任务
     */
    public void death();

    /**
     * 获取任务状态
     * @return
     */
    public TaskStatus getStatus();

    public long getId();


}
