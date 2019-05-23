package com.example.trader.Service.Impl;

import com.example.trader.Core.BrokerSocket.BrokerSocketContainer;
import com.example.trader.Dao.Repo.BrokerDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.OrderBook;
import com.example.trader.Service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BrokerServiceImpl implements BrokerService {
    private static ConcurrentHashMap<Integer, BrokerSocketContainer> brokerSocketContainers = new ConcurrentHashMap<>();

    @Autowired
    private BrokerDao brokerDao;

    @PostConstruct
    public void init(){
        System.out.println("[BrokerService.init] init");
        List<Broker> brokers = brokerDao.findAll();
        brokers.stream().forEach( e -> {
            BrokerSocketContainer brokerSocket = new BrokerSocketContainer(e);
            brokerSocket.init();
            brokerSocketContainers.put(e.getId(), brokerSocket);
        });
    }

    @Override
    public OrderBook getOrderBookByBrokerId(Integer bid){
        BrokerSocketContainer bsc = brokerSocketContainers.get(bid);
        if (bsc == null)
            return null;
        return bsc.getOrderBook();
    }

    @Override
    public Broker addBroker(Broker broker){
        Broker b = brokerDao.saveAndFlush(broker);
        BrokerSocketContainer brokerSocket = new BrokerSocketContainer(b);
        //brokerSocket.init();
        brokerSocketContainers.put(b.getId(), brokerSocket);
        return b;
    }

    @Override
    public boolean deleteBrokerById(Integer id){
        brokerSocketContainers.get(id).close();
        brokerSocketContainers.remove(id);
        brokerDao.deleteById(id);
        return true;
    }

    @Override
    public List<Broker> getBroker(){
        return brokerDao.findAll();
    }

    @Override
    public Broker getBrokerById(Integer id) {
        BrokerSocketContainer bsc = brokerSocketContainers.get(id);
        if (bsc == null) {
            return brokerDao.findById(id).get();
        }
        else
            return bsc.getBroker();
    }
}
