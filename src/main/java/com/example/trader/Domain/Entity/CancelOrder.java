package com.example.trader.Domain.Entity;


import com.example.trader.Domain.Order;

public class CancelOrder extends Order {
    Type targetType;
    String targetId;


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
}
