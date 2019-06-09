package com.example.trader.Service.Impl;


import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.AbstractOrderDao;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.OrderDao.LimitOrderDao;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.OrderDao.MarketOrderDao;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.OrderDao.StopOrderDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Service.OrderService;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Service.TraderSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private BrokerSideUserService brokerSideUserService;
    @Autowired
    private TraderSideUserService traderSideUserService;

    @Override
    public Object createWithStrategy(String username, Order order,
                                     ProcessorFactory.Parameter pp,
                                     SenderFactory.Parameter sp){

        Processor processor = processorFactory.create(pp);
        List<Order> orders = processor.process(order);

        Sender sender = senderFactory.create(sp);
        Object res  = sender.send(username, orders);

        return res;
    }

    @Override
    public Order create(String username, Order order, Integer brokerId){
        SenderFactory.Parameter sp = new SenderFactory.Parameter(SenderFactory.INSTANT_ONE, brokerId);

        Sender sender = senderFactory.create(sp);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        Map<String, String> res = (Map<String, String>)sender.send(username, orders);
        order.setId(res.get(brokerId));
        return order;
    }

    @Override
    public List<Order> findAll(String type, Integer brokerId) {
        Broker broker = brokerService.findById(brokerId);
        AbstractOrderDao dao = (AbstractOrderDao)daoFactory.create(broker, type);
        return dao.findAll();
    }

    @Override
    public Order findByIdAndType(String type, String id, Integer brokerId) {
        Broker broker = brokerService.findById(brokerId);
        AbstractOrderDao dao = (AbstractOrderDao)daoFactory.create(broker, type);
        return dao.findById(id);
    }

    @Override
    public List<Order> findByBrokerIdAndUsername(Integer brokerId, String username) {
        Broker broker = brokerService.findById(brokerId);


        String token = brokerSideUserService.getToken(username, brokerId);
        String brokerSideUsername = traderSideUserService.findByUsername(username).getBrokerSideUser(brokerId).getUsername();

        MarketOrderDao mdao = (MarketOrderDao) daoFactory.createWithToken(broker, Order.MARKET_ORDER, token);
        LimitOrderDao ldao = (LimitOrderDao) daoFactory.createWithToken(broker, Order.LIMIT_ORDER, token);
        StopOrderDao sdao = (StopOrderDao) daoFactory.createWithToken(broker, Order.STOP_ORDER, token);
        List<Order> mo = mdao.findByTraderName(brokerSideUsername);
        List<Order> lo = ldao.findByTraderName(brokerSideUsername);
        List<Order> so = sdao.findByTraderName(brokerSideUsername);
        mo.stream().forEach(e -> e.setType(Order.MARKET_ORDER));
        lo.stream().forEach(e -> e.setType(Order.LIMIT_ORDER));
        so.stream().forEach(e -> e.setType(Order.STOP_ORDER));
        mo.addAll(lo);
        mo.addAll(so);
        return mo;
    }
}
