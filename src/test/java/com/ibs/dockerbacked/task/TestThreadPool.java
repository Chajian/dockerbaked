package com.ibs.dockerbacked.task;

import com.ibs.dockerbacked.entity.task.BaseTask;
import com.ibs.dockerbacked.entity.task.TaskThread;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {
    ExecutorService executor;
    int max = 10000;
    List<TaskThread> tasks = new ArrayList<>();


    @Before
    public void init(){
        executor = Executors.newFixedThreadPool(10);//线程池

    }

    @Test
    public void addThread(){
        int current = -1;
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

            if(tasks.size()<=0||!tasks.get(current).add(baseTask)){
                current++;
                tasks.add(new TaskThread(max));
            }
        }
        run();
    }


    public void run(){
        for(int i = 0 ; i < tasks.size() ;i++){
            executor.execute(tasks.get(i));
        }
        while(true);
    }


}
