package com.ibs.dockerbacked.entity.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public abstract class BaseTask<T> implements DTask {
    int time;//计时器单位秒
    TaskStatus status;//任务状态
    private long id = 0;
    private static long coun = 0;
    protected T t;
    private ReentrantLock reentrantLock = new ReentrantLock();
    public BaseTask(int time) {
        reentrantLock.lock();
        try{
            id += coun++;
            this.time = time;
            status=TaskStatus.INIT;
            start();
            showStatus();
        }finally {
            reentrantLock.unlock();
        }

    }

    @Override
    public  void run() {
        reentrantLock.lock();
        try {
            status=TaskStatus.RUNNING;
            showStatus();
            if(time>0){
                time--;//减少活动时间
            }
            else{
                recall();//回调
                death();//死亡
            }
        }finally {
            reentrantLock.unlock();
        }

    }

    @Override
    public void recall() {
        reentrantLock.lock();
        try {
            status = TaskStatus.WAITING;
            showStatus();
        }finally {
            reentrantLock.unlock();
        }


    }

    @Override
    public  void death() {
        status = TaskStatus.DEATH;
        showStatus();
   }
//    @Override
//    public synchronized void run() {
//        status=TaskStatus.RUNNING;
//        showStatus();
//        if(time>0){
//            time--;//减少活动时间
//        }
//        else{
//            recall();//回调
//            death();//死亡
//        }
//    }
//
//    @Override
//    public synchronized void recall() {
//        status = TaskStatus.WAITING;
//        showStatus();
//    }
//
//    @Override
//    public synchronized void death() {
//        status = TaskStatus.DEATH;
//        showStatus();
//    }


    public void showStatus(){
        if(t!=null)
            log.info("Task"+t!=null?t.toString():""+":"+getStatus().toString());
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    public int getTime() {
        return time;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
