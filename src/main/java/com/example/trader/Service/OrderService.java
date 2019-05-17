package com.example.trader.Service;

import com.example.trader.Domain.Entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> createWithStrategy(Order order, String processStrategy, String sendStrategy, Integer brokerId, String type) throws Exception;

    Order create(Order order, Integer brokerId, String type) throws Exception;
}
