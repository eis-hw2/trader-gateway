package com.example.trader.Service.Impl;

import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.OrderBlotterDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Service.OrderBlotterService;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class OrderBlotterServiceImpl implements OrderBlotterService {

    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private BrokerService brokerService;
    @Autowired
    private BrokerSideUserService brokerSideUserService;

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
    public List<OrderBlotter> findByMarketDepthIdAndStartTimeAndEndTime(String traderSideUsername, Integer brokerId, String marketDepthId, String startTime, String endTime) {
        Broker broker = brokerService.findById(brokerId);
        String token = brokerSideUserService.getToken(traderSideUsername, brokerId);
        OrderBlotterDao dao = (OrderBlotterDao)daoFactory.createWithToken(broker, "OrderBlotter", token);

        return dao.findByMarketDepthIdAndInterval(marketDepthId, startTime, endTime);
    }
}
