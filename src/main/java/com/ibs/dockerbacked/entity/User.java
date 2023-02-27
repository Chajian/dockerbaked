package com.ibs.dockerbacked.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 * @author Chajian
 */
@Data
public class User extends TimeRecord{
    private int id;
    private String account;
    private String password;
    private List<PermissionGroup> permissionGroups;
}
