package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Send the Order to One Broker
*/
public class InstantOneSender extends InstantSender {

    private DaoFactory daoFactory;
    private BrokerSideUserService brokerSideUserService;

    public InstantOneSender(DaoFactory daoFactory, BrokerSideUserService brokerSideUserService){
        this.daoFactory = daoFactory;
        this.brokerSideUserService = brokerSideUserService;
    }

    @Override
    public Map<String, Order> send(String traderSideUsername, List<Order> orders) {
        Broker broker = getBrokers().get(0);
        String token = brokerSideUserService.getToken(traderSideUsername, broker.getId());

        AbstractOrderDao orderDao = daoFactory.createWithToken(broker, orders.get(0).getType(), token);

        // BrokerId, OrderId
        Map<String, Order> res = new HashMap<>();

        for(Order order: orders){
            res.put(broker.getId().toString(), orderDao.create(order));
        }

        return res;
    }
}
