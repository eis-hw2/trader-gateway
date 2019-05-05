package com.example.trader.Service.Impl;

import com.example.trader.Dao.OrderBlotterDao;
import com.example.trader.Entity.Order;
import com.example.trader.Entity.OrderBlotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl {
    @Autowired
    private OrderBlotterDao orderBlotterDao;

    private List<Order> VWAP(Order order){
        List<OrderBlotter> history = orderBlotterDao.getByFutureIdYesterday(order.getFutureId());

        return null;
    }

    private List<Order> TWAP(Order order){
        return null;
    }
}
