package com.ibs.dockerbacked.service;

import com.alipay.api.AlipayApiException;

/**
 * 支付接口
 * @author xyl
 */
public interface PayService {
    /**
     *创建支付宝订单-支付二维码
     * @param orderNo
     * @param price
     * @param subject
     * @return
     * @throws AlipayApiException
     */
    String aliPay(String orderNo,String price,String subject) throws AlipayApiException;

    /**
     * 查询订单
     * @return
     */
    String aliQuery();
    void wechatPay();
}
