package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Dao.Dao;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstantSender extends Sender {

    @Autowired
    private DaoFactory daoFactory;


    @Override
    public ResponseWrapper send(List<Broker> brokers, List<Order> orders) {
        Dao orderDao = daoFactory.create(brokers.get(0).getUrl(), orders.get(0).getType());
        for(Order order: orders){
            orderDao.create(order);
        }

        return null;
    }
}
