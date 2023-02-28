package com.ibs.dockerbacked.common;

/**
 * 通用返回结果，服务器响应的数据最终会封装成此对象
 *
 * @param <T>
 */
public class Result<T> {
    private Integer code; //编码
    private String message;//错误信息
    private T data;//返回数据

    //成功的信息
    public static <T> Result<T> success(Integer code, String message, T object) {
        Result<T> result = new Result<>();
        result.data = object;
        result.code = code;
        result.message = message;
        return result;
    }

    //失败的信息

    public static <T> Result<T> error(String message, Integer code) {
        Result<T> result = new Result<>();
        result.data = null;
        result.code = code;
        result.message = message;
        return result;
    }
}
