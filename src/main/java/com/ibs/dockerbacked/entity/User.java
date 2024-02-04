package com.ibs.dockerbacked.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mysql.cj.xdevapi.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author Chajian
 */

@TableName("users")
@Data
public class User extends TimeRecord implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String account;
    private String pwd;
    /*用户头像*/
    private String headUrl;
}
