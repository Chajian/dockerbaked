package com.ibs.dockerbacked.data;

public enum ResponseType {
    /*请求成功*/
    RESULT_SUCCESS(200, true,"请求成功"),
    LOGIN_FAIL(201,false,"登陆成功，但用户没绑定信息"),
    NOT_JOIN(202,false,"请求成功，但用户没有加入"),
    /*返回用户无绑定*/
//    RESULT_USER_NOT_BIND(200, true),
    /*返回用户已经绑定*/
//    RESULT_USER_ALREADY_BIND(200, true),
    /*返回删除指定数据成功*/
//    RESULT_DELETE_SUCCESS(204, true),
    /*返回失败*/
    RESULT_FAILURE(400, false,"请求失败"),
    /*权限不足*/
    OPERATION_NOT_PERMITTED(401, false,"权限不足"),
    /*认证异常*/
    AUTHENTICATION_EXCEPTION(402, false,"认证异常"),
    /*验证失败*/
    VERIFICATION_EXCEPTION(403,false,"验证失败"),

    FAIL_PARAM(407,false,""),
    /*服务器位置错误*/
    SERVER_ERROR_UNKNOWN(500, false,"服务器错误"),
    /*服务器没有找到此对象*/
    SERVER_NOT_FOUND_OBJECT(404, false,"找不到请求地址");
    private int code;
    private String des;
    private boolean status;

    ResponseType(int code, boolean status, String des) {
        this.code = code;
        this.status = status;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }
}
