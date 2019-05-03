package com.example.trader.Service;

import com.example.trader.Util.Wrapper.SessionWrapper;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.Session;
import java.io.IOException;

public interface WebSocketService {
    public void onOpen(Session session, String sid);

    public void onClose(Session session, String sid);

    public void onMessage(String message, Session session);

    public void onError(Session session, Throwable error);

    public void sendMessage(String sid, String message) throws IOException;

    public void broadcast(String message);

    public int getOnlineCount();
}
