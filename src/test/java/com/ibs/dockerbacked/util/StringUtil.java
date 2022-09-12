package com.ibs.dockerbacked.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串测试工具
 * @author Yezi
 */
@Slf4j
public class StringUtil {


    /**
     * 获取被映射端口
     * eg:8080:9900
     * 被映射端口:映射端口
     */
    @Test
    public void getSource(){
        String rule = "\\S+:";
        String info = "8080:9900";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()){
            log.info("result:"+matcher.group().substring(0,matcher.group().length()-1));
        }
        else{
            log.info("result:");
        }
    }
    /**
     * 获取映射端口
     * eg:8080:9900
     * 被映射端口:映射端口
     */
    @Test
    public void getTarget(){
        String rule = ":\\S*";
        String info = "8080:9900";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()){
            log.info("result:"+matcher.group().substring(1,matcher.group().length()));
        }
        else{
            log.info("result:");
        }
    }

    @Test
    public void getListPorts(){
        List<String> list = new ArrayList<>();
        String rule = "\\d*:\\d*";
        String info = "8080:9900,222:200,9090:999";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(info);

        while (matcher.find()){
            list.add(matcher.group());
        }
        log.info(Arrays.toString(list.toArray()));
    }

    @Test
    public void getEnvs(){
        List<String> list = new ArrayList<>();
        String rule = "\\S*=\\S*";
        String info = "sdfsf=j23jlsdf,089ssdf=skdljfl2";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(info);
        while (matcher.find()){
            list.add(matcher.group());
        }

        log.info(Arrays.toString(list.toArray()));
    }



}
