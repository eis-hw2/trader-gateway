package com.example.trader.Domain.Entity;


public class Future {
    String id;
    String MarketDepthId;
    String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketDepthId() {
        return MarketDepthId;
    }

    public void setMarketDepthId(String marketDepthId) {
        MarketDepthId = marketDepthId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
