package com.ibs.dockerbacked.entity.task;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 任务线程池
 * 采用时间片轮转
 * @author Yanglin
 */
public class TaskThread implements Runnable {
    /*任务队列*/
    private List<DTask> list;
    private boolean live;
    /*线程池最大容量*/
    int max = 20;

    public TaskThread() {
        init();
    }

    public TaskThread(int max) {
        this.max = max;
        init();
    }

    public void init(){
        live = true;
        list = new ArrayList<>();
    }

    @Override
    public void run() {
        while(live){
            Iterator<DTask> iterator = list.iterator();
            while(iterator.hasNext()){
                DTask task = iterator.next();
                if(task.getStatus()==TaskStatus.DEATH)
                    iterator.remove();
                else
                    task.run();
            }
            //休眠
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean add(DTask task){
        if(list.size() < max) {
            list.add(task);
            return true;
        }
        return false;
    }

}
