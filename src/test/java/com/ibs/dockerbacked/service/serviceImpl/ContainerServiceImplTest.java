package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.connection.ContainerModel;
import com.ibs.dockerbacked.connection.ImageModel;
import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.dto.AddContainer;
import com.ibs.dockerbacked.entity.dto.ContainerParam;
import com.ibs.dockerbacked.entity.dto.PageParam;
import com.ibs.dockerbacked.entity.dto.PullImages;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ContainerServiceImplTest {
    @Autowired
    private ContainerService containerService;

    @Autowired
    private ImageModel imageModel;
    @Autowired
    private ContainerModel containerModel;

    //查询容器
    @Test
    void getContainersByStatus() {
        List<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
//        System.out.println(containerService.getContainersByStatus(null, list));

    }

    @Test
    void getContainers() {
        ContainerParam containerParam = new ContainerParam();
//        containerParam
//        System.out.println(containerService.getContainers(containerParam, 1234L));
    }

    @Test
    void createContainer() {
    }

    @Test
    void getContainersByIdOrStatus() {
    }

    @Test
    void getImages() {
    }


    @Test
    void testPullImages() {
        PullImages pullImages = new PullImages();
        pullImages.setName("javaweb");
        pullImages.setTag("1.0");
        //通过指定的标签获取镜像,默认
        try {
            imageModel.pullImage(pullImages.getName(), pullImages.getTag());
        } catch (InterruptedException e) {
//            throw new CustomExpection(Constants.Internal_Server_Error, "拉取失败");
            log.info("拉取失败");
        }
    }

    @Test
    void testGetContainers() {

        String account = "1000";
        ContainerParam containerParam = new ContainerParam();
        containerParam.setAccount(account);
//         containerParam.setContainerId(0L);
//        containerParam.setStatus(List.of("1"));
        System.out.println(containerService.getContainers(containerParam, 1, 5, null));
    }

    @Test
    void testCreateContainer() {
        System.out.println(containerModel.inspectContainer("d00c2e6e829fec473abc4affba7f0333f408831caf9feafe23778b94a0faeeec"));
    }

    @Test
    void testGetContainersByIdOrStatus() {
    }

    @Test
    void testGetImages() {
    }

    @Test
    void pullImages() {
    }

    /**
     * 创建订单
     */
    @Test
    void createOrder() throws ParseException {
/**
 *
 */
        long userId = JwtUtil.getUserId("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJpZCI6MSwiZXhwIjoxNjc5NTQ4NTE1LCJhY2NvdW50IjoiMTAwMCJ9." +
                "SYj077kA2oEMdFRROiScQT9-jP_TRxq_Q1EX0F03wvY");
        Order order = new Order();
        order.setPacketId(3);
        order.setContainerId(0);
        order.setUserId((int) userId);
        order.setMoney(1000);
        order.setPayWay("0");
        order.setDescription("1234");
        String now = DateUtil.now();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        order.setCreatedAt(df.parse(now));
        containerService.createOrder(userId, order);
    }

    @Test
    void testCreateContainer1() {
        AddContainer addContainer = new AddContainer();
        addContainer.setImageName("mysql:latest");
        addContainer.setContainerName("mySql");
        List<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Aa123456789");
        addContainer.setEnvs(envs);
        addContainer.setPorts(List.of("3311:3306"));
        String containId = containerService.createContainer(addContainer, 1L);
        System.out.println(containId);
    }

    @Test
    void operateContainer() {
        containerService.operateContainer("d00c2e6e829fec473abc4affba7f0333f408831caf9feafe23778b94a0faeeec", "stop");
    }
}
