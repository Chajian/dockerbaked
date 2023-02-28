package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mysql.cj.xdevapi.Table;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 * @author Chajian
 */
@Data
public class Users extends TimeRecord{
    private int id;
    private String account;
    private String pwd;
    @TableField(exist = false)
    private List<PermissionGroup> permissionGroups;
}
