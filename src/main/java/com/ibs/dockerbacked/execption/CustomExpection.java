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
<<<<<<< HEAD
=======

    private String message; //异常消息

>>>>>>> 4aba4c5c63cc975568f6c1a10ed7576a9a7b6bb9
}
