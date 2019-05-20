package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Dao.DynamicDao;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Send the Order to Different Broker
* */
@Component
public class DistributeSender extends SplitSender {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private OrderScheduler orderScheduler;

    @Override
    public ResponseWrapper send(List<Broker> brokers, List<Order> orders) {
        Map<Order, DynamicDao> orderMap = new HashMap<>();

        int size = brokers.size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = brokers.get(curIndex % size);
            DynamicDao orderDao = daoFactory.create(curBroker.getGateway(), orders.get(0).getType());
            orderMap.put(order, orderDao);
            ++curIndex;
        }
        orderScheduler.addSplitOrder(orderMap);

        return null;
    }
}
