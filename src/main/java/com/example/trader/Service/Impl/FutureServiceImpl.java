package com.example.trader.Service.Impl;

import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.DynamicDao;
import com.example.trader.Dao.Repo.FutureDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Future;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FutureServiceImpl implements FutureService {
    @Autowired
    private BrokerService brokerService;
    @Autowired
    private DaoFactory daoFactory;

    @Override
    public List<Future> findAll(Integer brokerId) {
        Broker broker = brokerService.findById(brokerId);
        FutureDao dao = (FutureDao)daoFactory.create(broker, "Future");
        return dao.findAll();
    }

    @Override
    public Future findById(Integer brokerId, String id) {
        Broker broker = brokerService.findById(brokerId);
        FutureDao dao = (FutureDao)daoFactory.create(broker, "Future");
        return dao.findById(id);
    }
}
