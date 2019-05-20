package com.example.trader.Domain.Entity;

import javax.persistence.*;

@Entity
public class Broker {
    public static String NEW = "NEW";
    public static String DEAD = "DEAD";
    public static String ALIVE = "ALIVE";

    private static String writePort = "31000";
    private static String readPort = "31001";
    private static String webSocketPort = "31002";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String url;
    private String description;
    private String status;
    public Broker(){}

    public Broker(String url){
        this.url = url;
        this.status = NEW;
    }

    @Transient
    public String getWriteApi(){
        return url + ":" + writePort;
    }

    @Transient
    public String getReadApi(){
        return url + ":" + readPort;
    }

    @Transient
    public String getWebSocket(){
        return url + ":" + webSocketPort;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 100)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 100)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Broker broker = (Broker) o;

        if (id != null ? !id.equals(broker.id) : broker.id != null) return false;
        if (url != null ? !url.equals(broker.url) : broker.url != null) return false;
        if (description != null ? !description.equals(broker.description) : broker.description != null) return false;
        if (status != null ? !status.equals(broker.status) : broker.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
