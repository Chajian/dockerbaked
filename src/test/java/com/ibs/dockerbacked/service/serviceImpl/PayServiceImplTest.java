package com.ibs.dockerbacked.service.serviceImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.ibs.dockerbacked.DockerCloudBoot;
import com.ibs.dockerbacked.service.PayService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


public class PayServiceImplTest {
    String aliappid;
    String aliprivatekey;
    String alipublickey;
    public static AlipayClient alipayClient;

    @Before
    public void init() throws AlipayApiException {
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

    @Test
    public void aliPay() throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo("20150320010101002");
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
    @Test
    public void aliPaiOrder() throws AlipayApiException {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        List<GoodsDetail> goodsDetail = new ArrayList<GoodsDetail>();
        GoodsDetail goodsDetail0 = new GoodsDetail();
        goodsDetail0.setGoodsName("测试");
        goodsDetail0.setQuantity(1L);
        goodsDetail0.setPrice("10");
        goodsDetail0.setGoodsCategory("no");
        goodsDetail0.setCategoriesTree("0");
        goodsDetail0.setBody("哈哈哈");
        goodsDetail.add(goodsDetail0);
        model.setTotalAmount("88.88");
        model.setBuyerId("2088722003603074");
        model.setGoodsDetail(goodsDetail);
        model.setSubject("测试商品");
        model.setOutTradeNo("20150320010101001");
        request.setBizModel(model);
        AlipayTradeCreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    @Test
    public void createOrder() throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo("20150320010101001");
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功"+response.getBody());
        } else {
            System.out.println("调用失败");
        }
    }

    //订单查询
    @Test
    public void queryOrder() throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo("20150320010101001");
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    //退款查询
    @Test
    public void queryRefund() throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo("20150320010101001");
        model.setOutRequestNo("HZ01RF001");

        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    @Test
    public void recallOrder() throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setTradeNo("2088721003603066");

        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}
