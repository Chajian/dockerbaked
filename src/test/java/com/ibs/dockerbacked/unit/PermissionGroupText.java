package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.PermissionGroup;
import com.ibs.dockerbacked.service.PermissionGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

@SpringBootTest
public class PermissionGroupText {
    @Autowired
    PermissionGroupService permissionGroupService;

    @Test
    public void PermissionGroupAll() {
        System.out.println(permissionGroupService.list());
    }

    @Test
    public void PermissionGroupInsert() {
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setName("111");
        permissionGroup.setDescription("1");
        permissionGroupService.save(permissionGroup);
    }

    @Test
    public void PermissionGroupUpdate() {
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setId(2);
        permissionGroup.setName("123");
        permissionGroupService.saveOrUpdate(permissionGroup);
    }

    @Test
    public void PermissionGroupRemove() {
        permissionGroupService.removeById(2);
    }

}
