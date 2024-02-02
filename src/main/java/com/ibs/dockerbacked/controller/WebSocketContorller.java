package com.ibs.dockerbacked.controller;

import com.alipay.api.java_websocket.server.WebSocketServer;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.github.dockerjava.api.async.ResultCallback;
import com.ibs.dockerbacked.common.Constants;
import com.ibs.dockerbacked.connection.DashboardResultCallback;
import com.ibs.dockerbacked.entity.vo.Dashboard;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.service.ContainerService;
import com.ibs.dockerbacked.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
/**
 * 多对象模式
 *
 */
@Component
@ServerEndpoint("/ibs/api/containers/dashboard/{token}/{containerid}")
public class WebSocketContorller {
        //与某个客户端的连接会话，需要通过它来给客户端发送数据
        private Session session;
        private int userId;
        private String containerId;
        private static final CopyOnWriteArraySet<WebSocketContorller> webSockets = new CopyOnWriteArraySet<>();
        // 用来存在线连接数
        private static final Map<Integer, Session> sessionPool = new HashMap<Integer, Session>();
        private static final Map<String, DashboardResultCallback> dashboards = new HashMap<>();
        //保证对象唯一
        private static ContainerService containerService;

        private DashboardResultCallback resultCallback;

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
                this.resultCallback = resultCallback;
                dashboards.put(containerId,resultCallback);
                System.out.println("websocket消息: 有新的连接，总数为:" + webSockets.size());
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
            List<Dashboard> result = dashboards.get(containerId).getDashboards();
            session.getAsyncRemote().sendObject(result);
            switch (message){
                case "init":

                    return result.toString();

                case "current":

                    return result.get(result.size()-1).toString();
            }


            //发送dashboard信息
            return result.toString();
        }


        @OnClose
        public void onDeath(){
            resultCallback.onComplete();
        }

}
