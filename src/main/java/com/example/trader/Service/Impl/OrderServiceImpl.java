package com.example.trader.Service.Impl;

import com.example.trader.Domain.Order;
import com.example.trader.Service.OrderService;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ProcessorFactory processorFactory;
    @Autowired
    private SenderFactory senderFactory;

    @Override
    public List<Order> create(Order order, String processStrategy, String sendStrategy) {
        Processor processor = processorFactory.create(processStrategy);
        List<Order> orders = processor.process(order);

        Sender sender = senderFactory.create(sendStrategy);
        ResponseWrapper responseWrapper = sender.send(orders);
        List<Order> res = (List<Order>) responseWrapper.getBody();
        return res;
    }

    @Override
    public Order getById(String id) {
        return null;
    }
}
