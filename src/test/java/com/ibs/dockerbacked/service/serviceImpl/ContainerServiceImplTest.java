package com.ibs.dockerbacked.service.serviceImpl;

import cn.hutool.core.collection.CollUtil;
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
import com.ibs.dockerbacked.service.FileService;
import com.ibs.dockerbacked.service.SpaceService;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContainerServiceImplTest {
    @Autowired
    private ContainerService containerService;

    @Autowired
    private ImageModel imageModel;
    @Autowired
    private ContainerModel containerModel;
    @Autowired
    private SpaceService spaceService;

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
        imageModel.pullImage(pullImages.getName(), pullImages.getTag());
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
        System.out.println(containerModel.inspectContainer("7f556fb466c5244b071ed4790d074f2f6f9fa74c4edbf1077b34a6b6f077fff6"));
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
        order.setContainerId("0");
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
        addContainer.setContainerName("mySqlsfa23");
        List<String> envs = new ArrayList<>();
        envs.add("MYSQL_ROOT_PASSWORD=Aa123456789");
        addContainer.setEnvs(envs);
//        addContainer.setPorts(List.of("4313:3306"));
        String containId = containerService.createContainer(addContainer, 1L,null);
        System.out.println(containId);
    }

    @Test
    void operateContainer() {
        containerService.operateContainer("960d455fc8a73d67f3ad1212147360a0545cc90799ae822053b9f6039d7bc181", "stop");
    }

    @Test
    void uploadFileToContainer() throws FileNotFoundException {
        spaceService.createContainerSpace("Zekk","1-jskdfjlskdf");
        String containerPath = spaceService.getContainerSpace("Zekk","1-jskdfjlskdf");
        containerService.uploadFileToContainer("f44ba69f3c46","/","C:\\Users\\叶子\\Desktop\\springmvc\\dockerbaked\\pom.xml");


        while(true){
            System.out.println("skjdf");
        }
    }
}
