package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.Container;
import com.ibs.dockerbacked.entity.User;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.UserSerivce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContainerTest {
    @Autowired
    ContainerService containerService;
    @Autowired
    UserSerivce userSerivce;

    @Test
    public void containercontainerall() {
        System.out.println(containerService.list());
    }


    @Test
    public void containerInsert() {
        Container container = new Container();
        container.setImageId("hhh");
        User owner = userSerivce.getById(1);
        container.setOwnerId(owner.getId());

        containerService.save(container);
    }


    /**
     * 实体类Container成员变量owner与数据库Container的owner_id字段的类型不符无法添加
     * 其余正常
     */
    @Test
    public void containerUpdate() {
        Container container = new Container();
        container.setId(1);
        container.setImageId("1234");
        container.setName("1234");
        container.setState("1");
//        Users byId = userSerivce.getById(1);
//        container.setOwner(byId);
        container.setDescription("hhhh");
        containerService.updateById(container);

    }

    @Test
    public void containerDelete() {
        containerService.removeById(1);

    }
}
