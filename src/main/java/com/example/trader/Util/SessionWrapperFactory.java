package com.example.trader.Util;

import com.example.trader.Util.Wrapper.SessionWrapper;

import javax.websocket.Session;

public class SessionWrapperFactory {
    public static SessionWrapper create(Session session, String sid){
        return new SessionWrapper(session, sid);
    }

    public static SessionWrapper create(){
        return new SessionWrapper();
    }
}
