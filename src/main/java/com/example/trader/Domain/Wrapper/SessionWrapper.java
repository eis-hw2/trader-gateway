package com.example.trader.Domain.Wrapper;

import com.example.trader.Domain.Entity.Broker;

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
    private Broker broker;

    public SessionWrapper(){

    }

    public SessionWrapper(Session session, String sid, Broker broker){
        this.session = session;
        this.sid = sid;
        this.broker = broker;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }
}
