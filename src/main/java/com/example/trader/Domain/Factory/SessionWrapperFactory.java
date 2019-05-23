package com.example.trader.Domain.Factory;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Wrapper.SessionWrapper;
import javax.websocket.Session;

public class SessionWrapperFactory {
    public static SessionWrapper create(Session session, String sid, Broker broker){
        return new SessionWrapper(session, sid, broker);
    }

    public static SessionWrapper create(){
        return new SessionWrapper();
    }
}
