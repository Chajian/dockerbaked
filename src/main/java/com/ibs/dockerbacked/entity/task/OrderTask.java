package com.ibs.dockerbacked.entity.task;

import com.ibs.dockerbacked.entity.Order;

/**
 *
 * @author Chajian
 */
public class OrderTask extends BaseTask<Order> {
    //order info
    Order order;

    public OrderTask(int time, Order order) {
        super(time);
        this.order = order;
    }

    @Override
    public void start() {

    }

    @Override
    public synchronized void recall() {
        super.recall();
        //check order statue
        if(order.getState().equals("支付成功")){
            //create container and update user's info TODO
        }
        else if(order.getState().equals("未支付")){

        }
    }

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
