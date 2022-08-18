package com.ibs.dockerbacked.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBody {

    /*状态码*/
    public ResponseType code;
    /*消息*/
    public String message;
    /*传回的数据*/
    public Object data;


    /**
     * 生成返回200实体，默认空信息
     *
     * @param data 可序列化对象
     * @return resultBody Object
     */
    public static ResponseBody SUCCESS(Object data) {
        return new ResponseBody(ResponseType.RESULT_SUCCESS, null, data);
    }

    /**
     * 生成返回200实体，自定义返回消息
     *
     * @param data 可序列化对象
     * @return resultBody Object
     */
    public static ResponseBody SUCCESS(Object data, String msg) {
        return new ResponseBody(ResponseType.RESULT_SUCCESS, msg, data);
    }

    public static ResponseBody SUCCESS(String msg) {
        return new ResponseBody(ResponseType.RESULT_SUCCESS, msg, null);
    }


    public static ResponseBody Fail( String msg) {
        return new ResponseBody(ResponseType.RESULT_FAILURE, msg, null);
    }
    public static ResponseBody Fail( ResponseType type) {
        return new ResponseBody(type, type.getDes(), null);
    }
    public static ResponseBody Fail( ResponseType type,String msg) {
        return new ResponseBody(type, msg, null);
    }
    public static ResponseBody Fail(ResponseType type,String msg,Object data){
        return new ResponseBody(type,msg,data);
    }

    public int getCode() {
        return code.getCode();
    }


    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
