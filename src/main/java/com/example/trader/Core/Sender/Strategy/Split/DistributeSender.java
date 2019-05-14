package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Dao.Impl.OrderDao;
import com.example.trader.Domain.Broker;
import com.example.trader.Domain.Order;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
* Send the Order to Different Broker
* */
@Component
public class DistributeSender extends SplitSender {
    @Autowired
    private BrokerService brokerService;
    @Autowired
    private DaoFactory daoFactory;

    @Override
    public ResponseWrapper send(List<Order> orders) {
        List<OrderDao> orderDaos = new ArrayList<>();
        List<Broker> brokers = brokerService.getBroker();
        int size = brokers.size();
        int curIndex = 0;
        for (Order order: orders){
            Broker curBroker = brokers.get(curIndex % size);
            OrderDao orderDao = daoFactory.create(curBroker.getUrl(), OrderDao.class);
            orderDaos.add(orderDao);
            ++curIndex;
        }

        return null;
    }
}
