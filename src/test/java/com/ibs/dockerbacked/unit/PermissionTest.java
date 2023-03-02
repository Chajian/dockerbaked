package com.ibs.dockerbacked.unit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ibs.dockerbacked.entity.Permission;
import com.ibs.dockerbacked.entity.PermissionGroup;
import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.mapper.PermissionMapper;
import com.ibs.dockerbacked.service.PermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author sn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionTest {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void PermissionAll() {
        System.out.println(permissionService.list());
    }

    @Test
    public void PermissionInsert() {
        Permission Permission = new Permission();
        Permission.setName("权限");
        Permission.setDescription("1");
        permissionService.save(Permission);
    }

    @Test
    public void PermissionUpdate() {
        Permission Permission = new Permission();
        Permission.setId(1);
        Permission.setDescription("2");
        permissionService.saveOrUpdate(Permission);
    }

    @Test
    public void PermissionRemove() {
        permissionService.removeById(2);
    }

    @Test
    public void getPermissionsByUser(){
        permissionMapper.getPermissionsByUserAccount("1000").stream()
                .forEach(System.out::println);
    }
}
