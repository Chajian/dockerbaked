package com.ibs.dockerbacked.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 全局配置 编码
 * @author sn
 */
@AllArgsConstructor
@NoArgsConstructor
public enum Constants {


    CODE_200("成功!", 200), //成功
    CODE_400("权限不足",400),//权限不足
    CODE_401("客户端错误",401),//客户端错误
    CODE_Login_500("登录失败",500),//登录失败
    CODE_BatchREgister_501("注册失败",501),//注册失败
    Internal_Server_Error("服务器错误",401),//服务器错误

    EXEC_ERROR("执行指令失败!",401);

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
