package com.example.trader.Domain;

public class Broker {
    public static String NEW = "NEW";
    public static String DEAD = "DEAD";
    public static String ALIVE = "ALIVE";

    private String id;
    private String url;
    private String description;
    private String status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Broker(){}

    public Broker(String url){
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
