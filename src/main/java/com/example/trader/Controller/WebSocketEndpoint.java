package com.example.trader.Controller;

import com.example.trader.Service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{sid}")
@Component
public class WebSocketController {

    private final WebSocketService webSocketService;

    @Autowired
    public WebSocketService

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        //System.out.println("open");
        webSocketService.onOpen(session, sid);
    }

    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        //System.out.println("close");
        webSocketService.onClose(session, sid);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        /*
        System.out.println("message");
        try {
            session.getBasicRemote().sendText(message);
        }
        catch(Exception e){}*/
        webSocketService.onMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        //System.out.println("error");
        webSocketService.onError(session, error);
    }
}
