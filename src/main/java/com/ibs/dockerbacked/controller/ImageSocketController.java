package com.ibs.dockerbacked.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.connection.DashboardResultCallback;
import com.ibs.dockerbacked.entity.vo.Dashboard;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.service.ImageService;
import com.ibs.dockerbacked.task.event.PullImageEvent;
import com.ibs.dockerbacked.util.JwtUtil;
import io.swagger.annotations.Api;
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

/**
 * 镜像socket接口
 */
@Api(tags = "镜像socket接口")
@Component
@ServerEndpoint("/ibs/api/socket/command/{token}")
public class ImageSocketController {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private int userId;
    private final static String COMMAND = "command";

    private final static String MESSAGE = "message";
    private static final CopyOnWriteArraySet<ImageSocketController> webSockets = new CopyOnWriteArraySet<>();
    // 用来存在线连接数
    private static final Map<Integer, Session> sessionPool = new HashMap<Integer, Session>();
    //保证对象唯一

    private static ImageService imageService;


    @Autowired
    public void setImageService(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * 链接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "containerid") String containerId, @PathParam(value = "token") String token) {
        try {
            this.session = session;
            this.userId = JwtUtil.getUserId(token);
            webSockets.add(this);
            sessionPool.put(userId, session);
            System.out.println("websocket消息: 有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }




    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public String  onMessage(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        switch (jsonObject.getString(COMMAND)){
            case "image/get":
                long data = jsonObject.getLong(MESSAGE);
                return JSON.toJSONString(imageService.getPullImageEvent(data));
            case "images/get":
                List<PullImageEvent> events = imageService.getPullImageEvents();
                return JSON.toJSONString(events);
        }


        //发送dashboard信息
        return JSON.toJSONString(jsonObject);
    }


}
