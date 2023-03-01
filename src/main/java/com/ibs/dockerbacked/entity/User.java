package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mysql.cj.xdevapi.Table;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 * @author Chajian
 */
@Data
@TableName("users")
public class User extends TimeRecord{
    private int id;
    private String account;
    private String pwd;
}
