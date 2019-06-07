package com.example.trader.Service.Impl;


import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Repo.OrderDao.LimitOrderDao;
import com.example.trader.Dao.Repo.OrderDao.MarketOrderDao;
import com.example.trader.Dao.Repo.OrderDao.StopOrderDao;
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
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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

        MarketOrderDao mdao = (MarketOrderDao) daoFactory.createWithToken(broker, "MarketOrder", token);
        LimitOrderDao ldao = (LimitOrderDao) daoFactory.createWithToken(broker, "LimitiOrder", token);
        StopOrderDao sdao = (StopOrderDao) daoFactory.createWithToken(broker, "StopOrder", token);
        List<Order> res = mdao.findByTraderName(brokerSideUsername);
        res.addAll(ldao.findByTraderName(brokerSideUsername));
        res.addAll(sdao.findByTraderName(brokerSideUsername));
        return res;
    }
}
