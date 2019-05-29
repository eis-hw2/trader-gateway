package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Repo.DynamicDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* Send the Order to One Broker
* */
@Component
public class InstantSender extends Sender {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private BrokerSideUserService brokerSideUserService;

    @Override
    public Map<String, String> send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {
        Broker broker = brokers.get(0);
        String token = brokerSideUserService.getToken(traderSideUsername, broker.getId());
        AbstractOrderDao orderDao = daoFactory.create(broker, orders.get(0).getType(), token);

        // BrokerId, OrderId
        Map<String, String> res = new HashMap<>();

        for(Order order: orders){
            res.put(broker.getId().toString(), orderDao.create(order));
        }

        return res;
    }
}
