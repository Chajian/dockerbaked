package com.ibs.dockerbacked.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    long id;
    String account;
    int roleId;
    String pass;
    double money;
    String created;
}
