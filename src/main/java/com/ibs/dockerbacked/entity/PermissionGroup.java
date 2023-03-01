package com.ibs.dockerbacked.entity;

import lombok.Data;

import java.util.List;

/**
 * 权限组
 * @author Chajian
 */
@Data
public class PermissionGroup extends TimeRecord{
    private int id;
    private String name;
    private String description;
}
