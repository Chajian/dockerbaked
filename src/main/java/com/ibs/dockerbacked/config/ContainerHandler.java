package com.ibs.dockerbacked.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class ContainerHandler implements HandlerInterceptor {
    private static final String key = "containers";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public boolean isContainer(StringBuffer stringBuffer,int start){
        if(start<=stringBuffer.length())
            return false;
        String temp = "";
        char c = stringBuffer.charAt(start++);
        while(c!=47&&start < stringBuffer.length()){
            temp +=c;
            c=stringBuffer.charAt(start++);
        }
        if(temp.equals(key))
            return true;
        return isContainer(stringBuffer,start);
    }

}
