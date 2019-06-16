package com.example.trader.Service.Impl;

import com.example.trader.Dao.Repo.TraderSideDao.BrokerDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Service.BrokerService;
import com.example.trader.Util.LRUCache;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrokerServiceImpl implements BrokerService {

    @Autowired
    private BrokerDao brokerDao;

    private LRUCache<Integer, Broker> cache = new LRUCache<>(10);

    @Override
    public List<Broker> findAll(){
        return brokerDao.findAll();
    }

    @Override
    public Broker findById(Integer id) {
        Broker b = cache.get(id);
        if (b != null)
            return b;
        b = brokerDao.findById(id).get();

        if (b != null)
            cache.put(id, b);
        return b;
    }
}
