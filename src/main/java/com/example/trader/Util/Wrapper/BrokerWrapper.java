package com.example.trader.Util.Wrapper;

public class BrokerWrapper {
    private int id;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BrokerWrapper(){}

    public BrokerWrapper(String url){
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
