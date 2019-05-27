package com.example.trader.Service.Impl;

import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.OrderBlotterDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.OrderBlotterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class OrderBlotterServiceImpl implements OrderBlotterService {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private BrokerService brokerService;

    @Override
    public List<OrderBlotter> findAll(Integer brokerId) {
        Broker broker = brokerService.findById(brokerId);
        OrderBlotterDao dao = (OrderBlotterDao)daoFactory.create(broker, "OrderBlotter");

        return dao.findAll();
    }

    @Override
    public OrderBlotter findById(Integer brokerId, String id) {
        Broker broker = brokerService.findById(brokerId);
        OrderBlotterDao dao = (OrderBlotterDao)daoFactory.create(broker, "OrderBlotter");
        return dao.findById(id);
    }

    @Override
    public List<OrderBlotter> findByFutureIdAndTime(Integer brokerId, String futureId, Calendar startTime, Calendar endTime) {
        Broker broker = brokerService.findById(brokerId);
        OrderBlotterDao dao = (OrderBlotterDao)daoFactory.create(broker, "OrderBlotter");
        return null;
    }
}
