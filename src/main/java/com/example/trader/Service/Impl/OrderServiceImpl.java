package com.example.trader.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.trader.Dao.DaoFactory;
import com.example.trader.Dao.Impl.OrderDao;
import com.example.trader.Domain.Order;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.OrderService;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.Network.JsonHelper;
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

    private OrderDao getDao(String brokerId){

        return daoFactory.create(brokerService.getBrokerById(brokerId).getUrl(), OrderDao.class);
    }

    @Override
    public List<Order> createWithStrategy(Order order, String processStrategy, String sendStrategy, String brokerId, String type) throws Exception{
        Processor processor = processorFactory.create(processStrategy);
        List<Order> orders = processor.process(order);

        Sender sender = senderFactory.create(sendStrategy, brokerId, type);
        ResponseWrapper responseWrapper = sender.send(orders);

        if (responseWrapper.getStatus().equals(ResponseWrapper.ERROR))
            throw new Exception(JSON.toJSONString(responseWrapper.getBody()));
        return orders;
    }

    @Override
    public Order create(Order order, String brokerId, String type) throws Exception{
        Sender sender = senderFactory.create(SenderFactory.INSTANT, brokerId, type);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        ResponseWrapper responseWrapper = sender.send(orders);
        return order;
    }

    @Override
    public Order getById(String id, String brokerId) {
        return getDao(brokerId).getById(id);
    }
}
