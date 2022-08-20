package com.ibs.dockerbacked.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * jwt工具类
 * @author Yanglin
 * @date 2021/3/23
 * jwt相关理论知识
 * @url https://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html
 */
public class JwtUtils {

//    过期时间5分钟
    private static final long EXPIRE_TIME = 1000*60*30;
    private static final String SCREPT = "SzPtJsJxH2018@LeagueManager";

//    /**
//     *
//     * 消息验证
//     * @param token 前端传输过来的token
//     * @return 返回token验证是否成功
//     */
//    public static boolean verify(String token){
//        String number = getStudentNumber(token);
//        int id = getStudentId(token);
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(SCREPT);
//            JWTVerifier verifier = JWT.require(algorithm)
//                    .withClaim("number", number)
//                    .withClaim("id",id)
//                    .build();
//            DecodedJWT jwt = verifier.verify(token);
//            return true;
//        } catch (Exception exception) {
//            return false;
//        }
//    }

    /**
     * 验证token信息
     * @param token
     * @return
     */
    public static boolean verify(String token){
        String number = getAccount(token);
//        int id = getStudentId(token);
        try {
            Algorithm algorithm = Algorithm.HMAC256(getSCREPT());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", number)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 生成签名,5分钟后过期
     * @param account 账号
     * @return token
     */
    public static String sign(String account) {
        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SCREPT);
        // 附带username信息
        return JWT.create()
                //添加自定义Payload的字段 claim
                .withClaim("account", account)
                //添加特殊Payload的exp字段，用于设置过期时间
                .withExpiresAt(date)
                //设置生效的时间段
                //.withNotBefore()
                .sign(algorithm);
    }

//    /**
//     * 生成签名,5分钟后过期
//     * @param stuNumber 学号
//     * @param secret 服务器的密钥
//     * @param id 学生id
//     * @return token
//     */
//    public static String sign(String stuNumber, String secret,int id) {
//        Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        // 附带username信息
//        return JWT.create()
//            //添加自定义Payload的字段 claim
//            .withClaim("number", stuNumber)
//            .withClaim("id",id)
//            //添加特殊Payload的exp字段，用于设置过期时间
//            .withExpiresAt(date)
//            //设置生效的时间段
//            //.withNotBefore()
//            .sign(algorithm);
//    }

    /**
     *
     * 通过token返回account
     * @param token
     * @return 返回学生学号
     */
    public static String getAccount(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("account").asString();
    }

//    /**
//     * 通过token返回用户id
//     * @param token
//     * @return 返回学生id
//     */
//    public static int getStudentId(String token){
//        DecodedJWT jwt = JWT.decode(token);
//        return jwt.getClaim("id").asInt();
//    }

    public static String getSCREPT() {
        return SCREPT;
    }
}
