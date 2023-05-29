package com.ibs.dockerbacked.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Value("${pay.ali.id}")
    String aliappid;
    @Value("${pay.ali.privatekey}")
    String aliprivatekey;
    @Value("${pay.ali.publickey}")
    String alipublickey;
    AlipayClient alipayClient;

    PayServiceImpl() throws AlipayApiException {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId(aliappid);
        alipayConfig.setPrivateKey(aliprivatekey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipublickey);
        alipayConfig.setCharset("UTF8");
        alipayConfig.setSignType("RSA2");
        alipayClient = new DefaultAlipayClient(alipayConfig);
    }

    @Override
    public String aliPay(String orderNo,String price,String subject) throws AlipayApiException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(price);
        model.setSubject(subject);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            System.out.println("调用成功");
            return response.getBody();
        } else {
            System.out.println("调用失败");
            return null;
        }
    }

    @Override
    public String aliQuery() {
        return null;
    }

    @Override
    public void wechatPay() {

    }
}
