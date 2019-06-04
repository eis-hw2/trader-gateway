package com.example.trader.Domain.Entity;


import com.example.trader.Domain.Entity.Util.Links;

public class Future {
    private String id;
    private String futureName;
    private String marketDepthId;
    private String description;
    private Links _links;

    public Links get__links() {
        return _links;
    }

    public void set__links(Links __links) {
        this._links = __links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketDepthId() {
        return marketDepthId;
    }

    public void setMarketDepthId(String marketDepthId) {
        this.marketDepthId = marketDepthId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFutureName() {
        return futureName;
    }

    public void setFutureName(String futureName) {
        this.futureName = futureName;
    }
}
