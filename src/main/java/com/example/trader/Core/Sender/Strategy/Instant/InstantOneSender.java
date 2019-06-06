package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.Util.BrokerOrderPair;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
    public List<BrokerOrderPair> send(String traderSideUsername, List<Order> orders) {
        Broker broker = getBrokers().get(0);
        String token = brokerSideUserService.getToken(traderSideUsername, broker.getId());

        AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.createWithToken(broker, orders.get(0).getType(), token);

        // BrokerId, OrderId
        List<BrokerOrderPair> res = new ArrayList<>();

        for(Order order: orders){
            if (order.getTotalCount() == 0)
                continue;
            Order createdOrder = orderDao.create(order);
            createdOrder.setType(order.getType());
            res.add(new BrokerOrderPair(broker.getId(), createdOrder));
        }

        return res;
    }
}
