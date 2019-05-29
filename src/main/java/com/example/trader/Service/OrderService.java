package com.example.trader.Service;

import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Entity.Order;

import java.text.ParseException;
import java.util.List;

public interface OrderService {

    Object createWithStrategy(String username, Order order,
                              ProcessorFactory.Parameter pp,
                              SenderFactory.Parameter sp);

    Order create(String username, Order order, Integer brokerId);

    List<Order> findAll(String type, Integer brokerId);

    Order findById(String type,  String orderId, Integer brokerId);
}
