package com.example.trader.Core.Processor;

import com.example.trader.Domain.Entity.Order;

import java.util.List;

public interface Processor {
    List<Order> process(Order order);
}
