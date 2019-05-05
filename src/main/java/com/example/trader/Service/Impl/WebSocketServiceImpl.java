package com.example.trader.Service.Impl;

import com.example.trader.Service.WebSocketService;
import com.example.trader.Util.ResponseWrapperFactory;
import com.example.trader.Util.Wrapper.ResponseWrapper;
import com.example.trader.Util.Wrapper.SessionWrapper;
import com.example.trader.Util.SessionWrapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    static Logger log = LoggerFactory.getLogger(WebSocketServiceImpl.class);

    private static CopyOnWriteArraySet<SessionWrapper> sessionWrappers;
    private static Integer onlineCount = 0;

    @Override
    public void onOpen(Session session, @PathParam("sid") String sid) {
        SessionWrapper sessionWrapper = SessionWrapperFactory.create(session, sid);
        sessionWrappers.add(sessionWrapper);
        addOnlineCount();
        log.info("[WebSocket.onOpen] New Connection:" + sid + ", Number of Connection:" + getOnlineCount());
        try {
            String s = ResponseWrapperFactory.createResponseString(ResponseWrapper.SUCCESS, "connection success");
            sendMessageToSessionWrapper(sessionWrapper, s);
        } catch (IOException e) {
            log.error("[WebSocket.onOpen] IO Error");
        }
    }

    @Override
    public void onClose(Session session, String sid) {
        boolean isRemoved = sessionWrappers.removeIf(e -> e.getSid().equals(sid));
        if (isRemoved) {
            subOnlineCount();
            log.info("[WebSocket.onClose] Connection Closed, Number of Connection:" + getOnlineCount());
        } else {
            log.error("[WebSocket.onClose] Remove Error");
        }
    }

    @Override
    public void onMessage(String message, Session session) {
        //do nothing
    }

    @Override
    public void onError(Session session, Throwable error) {
        log.error("[WebSocket.onError] Error");
        error.printStackTrace();
    }

    @Override
    public void sendMessage(String sid, String message) throws IOException {
        log.info("[WebSocket] Send Message to " + sid);
        for (SessionWrapper sessionWrapper : sessionWrappers) {
            if (sessionWrapper.getSid().equals(sid)) {
                sendMessageToSessionWrapper(sessionWrapper, message);
                return;
            }
        }
    }

    @Override
    public void broadcast(String message) {
        log.info("[WebSocket] Send Message to All");
        for (SessionWrapper sessionWrapper : sessionWrappers) {
            try {
                sendMessageToSessionWrapper(sessionWrapper, message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    @Override
    public synchronized int getOnlineCount() {
        return onlineCount;
    }

    private void sendMessageToSessionWrapper(SessionWrapper sessionWrapper, String message) throws IOException {
        sessionWrapper.getSession().getBasicRemote().sendText(message);
    }

    private void addOnlineCount() {
        synchronized (onlineCount) {
            WebSocketServiceImpl.onlineCount++;
        }
    }

    private void subOnlineCount() {
        synchronized (onlineCount) {
            WebSocketServiceImpl.onlineCount--;
        }
    }
}
