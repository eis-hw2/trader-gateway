package com.example.trader.Controller;

import com.example.trader.Service.Impl.WebSocketServiceImpl;
import com.example.trader.Service.WebSocketService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/websocket/{sid}")
@Component
public class WebSocketEndpoint{

    private static WebSocketService webSocketService;

    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        WebSocketEndpoint.webSocketService = webSocketService;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        webSocketService.onOpen(session, sid);
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        webSocketService.onClose(session, sid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        webSocketService.onMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        webSocketService.onError(session, error);
    }
}
