package com.example.trader.Domain.Entity;

import com.example.trader.Domain.Entity.Util.Links;
import com.example.trader.Domain.Entity.Util.Side;
import com.example.trader.Domain.Entity.Util.Status;
import com.example.trader.Domain.Entity.Util.Type;
import io.swagger.annotations.ApiModelProperty;

public class Order {
    public static final String MARKET_ORDER = "MarketOrder";
    public static final String LIMIT_ORDER = "LimitOrder";
    public static final String STOP_ORDER = "StopOrder";
    public static final String CANCEL_ORDER = "CancelOrder";

    public static final Order ERROR_ORDER = new Order("error");

    private Order(String error){
        this.id = error;
    }

    private String id;
    private int unitPrice;
    private Side side;
    private String marketDepthId;
    private Status status;
    private int count;
    private int totalCount;
    private String futureName;
    private Type targetType;
    private String targetId;
    private String type;
    private String traderName;
    private String creationTime;
    private Links __links;
    @ApiModelProperty(notes = "与用户下单无关的id")
    private String clientId;

    @ApiModelProperty(notes = "止损价格")
    private int stopPrice;
    @ApiModelProperty(notes = "StopOrder 转换的时间")
    private String statusSwitchTime;

    public Links get__links() {
        return __links;
    }

    public void set__links(Links __links) {
        this.__links = __links;
    }

    public String getTraderName() {
        return traderName;
    }

    public void setTraderName(String traderName) {
        this.traderName = traderName;
    }

    /**
     * Important
     * 新加属性后需要修改该函数
     * @param o
     */
    public Order(Order o){

        id = o.getId();
        unitPrice = o.getUnitPrice();
        side = o.getSide();
        marketDepthId = o.getMarketDepthId();
        status = o.getStatus();
        count = o.getCount();
        totalCount = o.getTotalCount();
        futureName = o.getFutureName();
        targetType = o.getTargetType();
        targetId = o.getTargetId();
        traderName = o.getTraderName();
        type = o.getType();
        stopPrice = o.getStopPrice();
    }

    public Order(){}

    public Type getTargetType() {
        return targetType;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public String getMarketDepthId() {
        return marketDepthId;
    }

    public void setMarketDepthId(String marketDepthId) {
        this.marketDepthId = marketDepthId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFutureName() {
        return futureName;
    }

    public void setFutureName(String futureName) {
        this.futureName = futureName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(int stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getStatusSwitchTime() {
        return statusSwitchTime;
    }

    public void setStatusSwitchTime(String statusSwitchTime) {
        this.statusSwitchTime = statusSwitchTime;
    }
}
