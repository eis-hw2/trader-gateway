package com.example.trader.Service;

import com.example.trader.Domain.Order;

import java.util.List;

public interface OrderService {

    Order getById(String id, String brokerId);

    List<Order> createWithStrategy(Order order, String processStrategy, String sendStrategy, String brokerId, String type) throws Exception;

    Order create(Order order, String brokerId, String type) throws Exception;
}
