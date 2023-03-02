package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mysql.cj.xdevapi.Table;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author Chajian
 */

@TableName("users")
@Data

public class User extends TimeRecord {
    private Integer id;
    private String account;
    private String pwd;
}
