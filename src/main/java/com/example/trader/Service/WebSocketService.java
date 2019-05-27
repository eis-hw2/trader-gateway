package com.example.trader.Service;

import javax.websocket.Session;
import java.io.IOException;

public interface WebSocketService {
    public void onOpen(Session session, String sid, Integer bid);

    public void onClose(Session session, String sid, Integer bid);

    public void onMessage(String message, Session session);

    public void onError(Session session, Throwable error);

    public void sendMessage(String sid, String message) throws IOException;

    public void broadcast(String message);

    public int getOnlineCount();
}
