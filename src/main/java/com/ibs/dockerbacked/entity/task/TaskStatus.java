package com.ibs.dockerbacked.entity.task;

/**
 * 任务状态
 * @author Yanglin
 */
public enum TaskStatus {
    /*初始化状态*/
    INIT,
    /*等待状态*/
    WAITING,
    /*运行状态*/
    RUNNING,
    /*死亡状态*/
    DEATH;
}
