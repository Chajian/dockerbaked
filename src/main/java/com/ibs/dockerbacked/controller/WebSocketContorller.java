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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
@ServerEndpoint("/ibs/api/containers/dashboard/{token}/{containerid}")
/**
 * 多对象模式
 *
 */
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

        @Autowired
        public void setContainerService(ContainerService containerService){
            this.containerService = containerService;
        }
        /**
         * 链接成功调用的方法
         */
        @OnOpen
        public void onOpen(Session session, @PathParam(value = "token") String token,@PathParam(value = "containerid") String containerId) {
            try {
                this.session = session;
                this.userId = JwtUtil.getUserId(token);
                this.containerId = containerId;
                webSockets.add(this);
                sessionPool.put(userId, session);
                DashboardResultCallback resultCallback = (DashboardResultCallback) containerService.getDashboard(containerId);
                dashboards.put(containerId,resultCallback);
                log.info("websocket消息: 有新的连接，总数为:" + webSockets.size());
            } catch (Exception e) {
            }
        }


        /**
         * 收到客户端消息后调用的方法
         */
        @OnMessage
        public void onMessage(String command) {
            if(!containerService.hasContainer(containerId,userId))
                throw new CustomExpection(Constants.CODE_400);
            //发送dashboard信息
            List<Dashboard> result = dashboards.get(containerId).getDashboards();
            session.getAsyncRemote().sendObject(result);
        }
        /**
         * 此为单点消息
         */
        public void sendOneMessage(String userId, String message) {
            Session session = sessionPool.get(userId);
            if (session != null && session.isOpen()) {
                try {
                    log.info("websocket消: 单点消息:" + message);
                    session.getAsyncRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

}
