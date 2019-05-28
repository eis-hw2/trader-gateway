package com.example.trader.Domain.Entity.Util;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.BrokerSideUser;
import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Service.BrokerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderSideUserLoginReturned {
    private String id;
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
    private Map<String, BrokerSideUser> brokerSideUsers = new HashMap<>();
    private List<Broker> brokers;

    public TraderSideUserLoginReturned (TraderSideUser traderSideUser, BrokerService brokerService){
        this.id = traderSideUser.getId();
        this.username = traderSideUser.getUsername();
        this.password = traderSideUser.getPassword();
        this.roles = traderSideUser.getRoles();
        this.brokerSideUsers = traderSideUser.getBrokerSideUsers();
        this.brokers = new ArrayList<>();

        traderSideUser.getBrokerSideUsers().entrySet()
                .stream()
                .forEach(e -> {
                    Integer brokerId = e.getValue().getBrokerId();
                    Broker broker = brokerService.findById(brokerId);
                    this.brokers.add(broker);
                });
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, BrokerSideUser> getBrokerSideUsers() {
        return brokerSideUsers;
    }

    public void setBrokerSideUsers(Map<String, BrokerSideUser> brokerSideUsers) {
        this.brokerSideUsers = brokerSideUsers;
    }

    public List<Broker> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<Broker> brokers) {
        this.brokers = brokers;
    }
}
