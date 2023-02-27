package com.ibs.dockerbacked.entity;

import java.util.List;

/**
 * 权限组
 * @author Chajian
 */
public class PermissionGroup extends TimeRecord{
    private int id;
    private String name;
    private String descrption;
    private List<Permission> permissions;


}
