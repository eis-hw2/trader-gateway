package com.example.trader.Service.Impl;


import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.OrderService;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ProcessorFactory processorFactory;
    @Autowired
    private SenderFactory senderFactory;
    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private BrokerService brokerService;

    @Override
    public List<Order> createWithStrategy(Order order, String processStrategy, String sendStrategy, Integer brokerId){
        Processor processor = processorFactory.create(processStrategy);
        List<Order> orders = processor.process(order);

        List<Broker> brokers = senderFactory.getBroker(sendStrategy, brokerId);
        Sender sender = senderFactory.create(sendStrategy);
        ResponseWrapper responseWrapper = sender.send(brokers, orders);

        /*
        if (responseWrapper.getStatus().equals(ResponseWrapper.ERROR))
            throw new Exception(JSON.toJSONString(responseWrapper.getBody()));
            */
        return orders;
    }

    @Override
    public Order create(Order order, Integer brokerId){
        Sender sender = senderFactory.create(SenderFactory.INSTANT);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        List<Broker> brokers = senderFactory.getBroker(SenderFactory.INSTANT, brokerId);
        ResponseWrapper responseWrapper = sender.send(brokers, orders);
        return order;
    }

    @Override
    public List<Order> getAll(String type, Integer brokerId) {
        Broker broker = brokerService.getBrokerById(brokerId);
        AbstractOrderDao dao = (AbstractOrderDao)daoFactory.create(broker, type);
        return dao.getAll();
    }
}
