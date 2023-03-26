package com.ibs.dockerbacked.task;

import com.ibs.dockerbacked.entity.task.BaseTask;
import com.ibs.dockerbacked.entity.task.TaskThread;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程测试
 * @author Yanglin
 */
public class TestTaskThread {
    TaskThread taskThread;
    List<TaskThread> tasks = new ArrayList<>();
    int current = 0;
    List<Thread> list = new ArrayList<>();
    int max = 10000;

    @Before
    public void init(){
        tasks.add(new TaskThread(max));
    }


    @Test
    public void addTask(){
        for(int i = 0 ; i < max*10 ; i++){
            BaseTask baseTask = new BaseTask(i) {
                @Override
                public void start() {
                }

                @Override
                public synchronized void run() {
                    super.run();
                    System.out.print("当前倒计时:"+getTime()+"-");
                }

                @Override
                public synchronized void death() {
                    super.death();
                    System.out.println("wocao");
                }
            };

            if(!tasks.get(current).add(baseTask)){
                current++;
                tasks.add(new TaskThread(max));
            }
        }
        run();

    }

    public void run(){
        for(int i = 0 ; i < tasks.size() ;i++){
            Thread t = new Thread(tasks.get(i));
            t.start();
            list.add(t);
        }
        System.out.println("ss");
        while(true);
    }

}
