package com.ibs.dockerbacked.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具
 * @author Yezi
 */
public class StringUtil {

    /**
     * 获取被映射端口
     * @param mapPort
     * @return
     */
    public static String getSourcePort(String mapPort){
        String rule = "\\S+:";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(mapPort);
        if(matcher.find()){
            return matcher.group().substring(0,matcher.group().length()-1);
        }
        return null;
    }

    /**
     * 获取映射端口
     * @param mapPort
     * @return
     */
    public static String getTargetPort(String mapPort){
        String rule = ":\\S*";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(mapPort);
        if(matcher.find()){
            return matcher.group().substring(1,matcher.group().length());
        }
        return null;
    }


    /**
     * 通过获取映射端口表
     * 读取字符串格式:8080:9900,222:200,9090:999
     * @return
     */
    public static List<String> getPortsByString(String ports){
        List<String> list = new ArrayList<>();
        String rule = "\\d*:\\d*";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(ports);

        while (matcher.find()){
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 获取env
     * @return
     */
    public static List<String> getEnvsByString(String info){
        List<String> envs = new ArrayList<>();
        String rule = "\\S*=\\S*";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(info);
        while (matcher.find()){
            envs.add(matcher.group());
        }
        return envs;
    }


}
