package com.ibs.dockerbacked.task.event;

public class BaseListener<T> implements Listener {
    private T t;

    @Override
    public void onListen(Event event) {
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
