package com.example.trader.Core.Sender;

import com.example.trader.Domain.Entity.Order;

import java.util.List;

public interface Sender {

    Object send(String traderSideUsername, List<Order> orders);
}
