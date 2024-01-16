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
    CONTAINER_REPEAT_NAME("容器名重复",401),
    CONTAINER_STATS_ERROR("获取容器资源占用失败",401),
    HARDWARE_NULL("硬件信息为空",401),
    IMAGE_NOT_EXIST("镜像不存在",401),
    FILE_NOT_EXIST("文件不存在",401),
    FILE_AREALY_EXIST("文件已经存在",401),
    FILE_WRITE_FAIL("文件写入失败",401),
    PATH_NOT_EXIST("目录不存在",401),

    PACKET_NULL("套餐信息为空",401),
    NULL("存在空信息",401),

    PAY_FAIL("支付失败",401),
    PAY_NOT_ENOUGH_MONEY("支付失败，钱包金额不够",401),
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
