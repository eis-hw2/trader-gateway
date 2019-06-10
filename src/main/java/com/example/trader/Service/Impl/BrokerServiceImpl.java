package com.example.trader.Service.Impl;

import com.example.trader.Dao.Repo.TraderSideDao.BrokerDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerServiceImpl implements BrokerService {

    @Autowired
    private BrokerDao brokerDao;

    @Override
    public List<Broker> findAll(){
        return brokerDao.findAll();
    }

    @Override
    public Broker findById(Integer id) {
        return brokerDao.findById(id).get();
    }
}
