package com.example.trader.Service;

import com.example.trader.Domain.Entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> createWithStrategy(String username, Order order, String processStrategy, String sendStrategy, Integer brokerId);

    Order create(String username, Order order, Integer brokerId);

    List<Order> findAll(String type, Integer brokerId);

    Order findById(String type, Integer brokerId, String id);
}
