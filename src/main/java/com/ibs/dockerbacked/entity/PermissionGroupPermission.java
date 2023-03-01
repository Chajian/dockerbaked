package com.ibs.dockerbacked.entity;

import lombok.Data;

/**
 * 权限和权限组的关系
 * @author Chajian
 */
@Data
public class PermissionGroupPermission {
    private int id;
    private int groupId;
    private int permissionId;
}
