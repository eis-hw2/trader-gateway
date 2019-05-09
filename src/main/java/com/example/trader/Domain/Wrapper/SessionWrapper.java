package com.example.trader.Domain.Wrapper;

import javax.websocket.Session;

public class SessionWrapper {

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private Session session;
    private String sid;

    public SessionWrapper(){

    }

    public SessionWrapper(Session session, String sid){
        this.session = session;
        this.sid = sid;
    }
}
