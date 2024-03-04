package com.ibs.dockerbacked.controller;

import com.alibaba.fastjson.JSON;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.connection.DashboardResultCallback;
import com.ibs.dockerbacked.entity.vo.Dashboard;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import com.ibs.dockerbacked.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/ibs/api/socket/webshell/{token}/{containerid}")
public class WebShellController {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private int userId;
    private String containerId;
    private static final CopyOnWriteArraySet<WebShellController> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static final Map<Integer, Session> sessionPool = new HashMap<Integer, Session>();
    private static final Map<String, DashboardResultCallback> dashboards = new HashMap<>();
    //保证对象唯一
    private static ContainerService containerService;
    private String location = "/";


    @Autowired
    public void setContainerService(ContainerService containerService){
        this.containerService = containerService;
    }
    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "containerid") String containerId, @PathParam(value = "token") String token) {
        try {
            this.session = session;
            this.userId = JwtUtil.getUserId(token);
            this.containerId = containerId;
            webSockets.add(this);
            sessionPool.put(userId, session);
            DashboardResultCallback resultCallback = (DashboardResultCallback) containerService.getDashboard(containerId);
            dashboards.put(containerId,resultCallback);
            System.out.println("webshell消息: 有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }




    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public String  onMessage(String message) {
        if(!containerService.hasContainer(containerId,userId))
            throw new CustomExpection(Constants.CODE_400);
        if(!containerService.getContainerStatus(containerId).equals("running")){
            throw new CustomExpection(Constants.CONTAINER_STATUS_NOT_INT_RUNNING);
        }
        String result = containerService.execCommand(containerId,message,location);
        //cd 执行触发
        String tempPath = StringUtil.execCm(message,location);
        if(tempPath!=null)
            location = tempPath;
        //发送dashboard信息
        return JSON.toJSONString(result);
    }


    @OnClose
    public void onDeath(){

    }
}
