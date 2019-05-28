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

import java.util.List;

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
    public int send(String traderSideUsername, List<Broker> brokers, List<Order> orders) {
        Broker broker = brokers.get(0);
        String token = brokerSideUserService.getToken(traderSideUsername, broker.getId());
        AbstractOrderDao orderDao = daoFactory.create(broker, orders.get(0).getType(), token);
        for(Order order: orders){
            orderDao.create(order);
        }

        return 0;
    }
}
