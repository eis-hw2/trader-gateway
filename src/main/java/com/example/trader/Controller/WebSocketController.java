package com.example.trader.Controller;

import com.example.trader.Service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketController {

    @Autowired
    WebSocketService webSocketService;

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
