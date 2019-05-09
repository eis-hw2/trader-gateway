package com.example.trader.Core.Sender;

import com.example.trader.Domain.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;

import java.util.List;

public abstract class Sender {
    public abstract ResponseWrapper send(List<Order> orders);
}
