package com.ibs.dockerbacked.unit;


import com.ibs.dockerbacked.entity.UserPermissionGroup;
import com.ibs.dockerbacked.service.UserPermissionGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserPermissionGroupTest {
    @Autowired
    UserPermissionGroupService userPermissionGroupService;

    @Test
    public void userPermissionGroupAll() {
        System.out.println(userPermissionGroupService.list());
    }

    @Test
    public void userPermissionGroupInsert() {
        UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
        userPermissionGroup.setUserId(1);
        userPermissionGroup.setGroupId(1);
        userPermissionGroupService.save(userPermissionGroup);
    }

    @Test
    public void userPermissionGroupUpdate() {
        UserPermissionGroup userPermissionGroup = new UserPermissionGroup();
        userPermissionGroup.setId(3);
        userPermissionGroup.setUserId(3);
        userPermissionGroup.setGroupId(3);
        userPermissionGroupService.saveOrUpdate(userPermissionGroup);
    }

    @Test
    public void userPermissionGroupRemove() {
        userPermissionGroupService.removeById(3);
    }
}
