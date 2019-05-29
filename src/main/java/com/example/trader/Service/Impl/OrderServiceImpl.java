package com.example.trader.Service.Impl;


import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.OrderService;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
    public List<Order> createWithStrategy(String username, Order order,
                                          String processStrategy,
                                          String sendStrategy,
                                          Integer brokerId,
                                          String startTime,
                                          String endTime) throws ParseException{
        Calendar start, end;
        if (startTime.equals(DateUtil.TOMMOROW_OPEN))
            start = DateUtil.getTomorrowOpenTime();
        else {
            start = DateUtil.stringToCalendar(startTime, DateUtil.datetimeFormat);
        }

        if (endTime.equals(DateUtil.TOMMOROW_CLOSE))
            end = DateUtil.getTomorrowCloseTime();
        else {
            end = DateUtil.stringToCalendar(endTime, DateUtil.datetimeFormat);
        }

        Processor processor = processorFactory.create(processStrategy, start, end);
        List<Order> orders = processor.process(order);

        List<Broker> brokers = senderFactory.getBroker(sendStrategy, brokerId);
        Sender sender = senderFactory.create(sendStrategy);
        int res  = sender.send(username, brokers, orders);

        /*
        if (responseWrapper.getStatus().equals(ResponseWrapper.ERROR))
            throw new Exception(JSON.toJSONString(responseWrapper.getBody()));
            */
        return orders;
    }

    @Override
    public Order create(String username, Order order, Integer brokerId){
        Sender sender = senderFactory.create(SenderFactory.INSTANT);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        List<Broker> brokers = senderFactory.getBroker(SenderFactory.INSTANT, brokerId);
        int res = sender.send(username, brokers, orders);
        return order;
    }

    @Override
    public List<Order> findAll(String type, Integer brokerId) {
        Broker broker = brokerService.findById(brokerId);
        AbstractOrderDao dao = (AbstractOrderDao)daoFactory.create(broker, type);
        return dao.findAll();
    }

    @Override
    public Order findById(String type, Integer brokerId, String id) {
        Broker broker = brokerService.findById(brokerId);
        AbstractOrderDao dao = (AbstractOrderDao)daoFactory.create(broker, type);
        return dao.findById(id);
    }
}
