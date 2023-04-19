package com.ibs.dockerbacked.entity.task;

import com.ibs.dockerbacked.entity.Order;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Chajian
 */
public class OrderTask extends BaseTask<Order> {
    //order info
   private Order order;
   private ReentrantLock reentrantLock = new ReentrantLock();
    public OrderTask(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void start() {

    }

    @Override
    public  void run() {
        reentrantLock.lock();
        try {
            super.run();
            if(order.getState().equals("支付成功")||order.getState().equals("未支付")){
                recall();
            }
        }finally {
            reentrantLock.unlock();
        }

    }

    @Override
    public void recall() {

        reentrantLock.lock();
        try {
            super.recall();
            //check order statue
            if(order.getState().equals("支付成功")){
                //create container and update user's info TODO
            }
            else if(order.getState().equals("未支付")){

            }
        }finally {
            reentrantLock.unlock();
        }


   }
//    @Override
//    public synchronized void run() {
//        super.run();
//        if(order.getState().equals("支付成功")||order.getState().equals("未支付")){
//            recall();
//        }
//    }
//
//    @Override
//    public synchronized void recall() {
//        super.recall();
//        //check order statue
//        if(order.getState().equals("支付成功")){
//            //create container and update user's info TODO
//        }
//        else if(order.getState().equals("未支付")){
//
//        }
//    }

    /**
     * 判断Task是否指定指定的order
     * @param id
     * @return
     */
    public boolean isTargetTask(int id){
        return order.getId() == id;
    }

    public String getOrderState(){
        return order.getState();
    }

    public void setOrderState(String state){
        order.setState(state);
    }

    public int getOrderId(){
        return order.getId();
    }
}
