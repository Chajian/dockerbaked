package com.ibs.dockerbacked.task.event;

/**
 * 延时触发器
 */
public class DelayDriver extends BaseDriver {
    int delay;
    @Override
    public void Triger(Event event) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        super.Triger(event);
    }
}
