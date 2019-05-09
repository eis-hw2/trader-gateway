package com.example.trader.Core.Processor;

import com.example.trader.Domain.Order;

import java.util.List;

public abstract class Processor {
    public abstract List<Order> process(Order order);
}
