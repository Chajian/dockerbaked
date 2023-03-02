package com.ibs.dockerbacked.execption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomExpection extends RuntimeException implements Serializable {
    private Integer code;  //状态编码

    private String message; //异常消息

}
