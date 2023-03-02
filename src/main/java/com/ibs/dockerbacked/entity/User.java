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
<<<<<<< HEAD
@Data
public class User extends TimeRecord {
    private int id;
=======
public class User extends TimeRecord{
    private Integer id;
>>>>>>> 4aba4c5c63cc975568f6c1a10ed7576a9a7b6bb9
    private String account;
    private String pwd;
}
