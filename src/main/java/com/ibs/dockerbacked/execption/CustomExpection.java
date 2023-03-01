package com.ibs.dockerbacked.execption;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CustomExpection extends RuntimeException implements Serializable {

    private String message; //异常消息
    private Integer code;  //状态编码

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
