package com.ibs.dockerbacked.unit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.entity.PermissionGroup;
import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.mapper.UserMapper;
import com.ibs.dockerbacked.mapper.UserPermissionGroupMapper;
import com.ibs.dockerbacked.service.PermissionGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)

@SpringBootTest
public class PermissionGroupText {
    @Autowired
    PermissionGroupService permissionGroupService;

    @Autowired
    UserPermissionGroupMapper userPermissionGroupMapper;

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
