package com.example.trader.Domain.Entity;

import com.example.trader.Domain.Order;

public class StopOrder extends Order {
    Type targetType;

    public Type getTargetType() {
        return targetType;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
    }
}
