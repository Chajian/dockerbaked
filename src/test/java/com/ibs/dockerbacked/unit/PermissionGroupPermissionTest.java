package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.Permission;
import com.ibs.dockerbacked.entity.PermissionGroupPermission;
import com.ibs.dockerbacked.service.PermissionGroupPermissionService;
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
public class PermissionGroupPermissionTest {
    @Autowired
    private PermissionGroupPermissionService permissionGroupPermissionService;

    @Test
    public void PermissionAll() {
        System.out.println(permissionGroupPermissionService.list());
    }

    @Test
    public void PermissionInsert() {
        PermissionGroupPermission permissionGroupPermission = new PermissionGroupPermission();
        permissionGroupPermission.setGroupId(1);
        permissionGroupPermission.setPermissionId(1);
        permissionGroupPermissionService.save(permissionGroupPermission);
    }

    @Test
    public void PermissionUpdate() {
        PermissionGroupPermission permissionGroupPermission = new PermissionGroupPermission();
        permissionGroupPermission.setId(2);
        permissionGroupPermission.setGroupId(3);
        permissionGroupPermission.setPermissionId(1);
        permissionGroupPermissionService.saveOrUpdate(permissionGroupPermission);
    }

    @Test
    public void PermissionRemove() {
        permissionGroupPermissionService.removeById(2);
    }
}
