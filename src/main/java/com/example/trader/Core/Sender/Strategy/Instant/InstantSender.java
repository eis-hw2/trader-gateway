package com.example.trader.Core.Sender.Strategy.Instant;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Dao.Impl.OrderDao;
import com.example.trader.Domain.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class InstantSender extends Sender {
    private String broker;

    @Autowired
    private DaoFactory daoFactory;

    private OrderDao orderDao;

    @Override
    public ResponseWrapper send(List<Order> orders) {
        orderDao = daoFactory.create(broker, OrderDao.class);
        for(Order order: orders){
            orderDao.create(orders.get(0));
        }

        return null;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }
}
