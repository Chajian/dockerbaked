package com.ibs.dockerbacked.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserEntity {
    long id;
    String account;
    String pass;
    double money;
    String created;
}
