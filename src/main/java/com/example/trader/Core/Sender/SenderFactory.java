package com.example.trader.Core.Sender;

import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Core.Sender.Strategy.Instant.InstantDistributeSender;
import com.example.trader.Core.Sender.Strategy.Instant.InstantOneSender;
import com.example.trader.Core.Sender.Strategy.Delay.DelayDistributeSender;
import com.example.trader.Core.Sender.Strategy.Delay.DelayOneSender;
import com.example.trader.Core.Sender.Strategy.InstantSender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class SenderFactory {

    @Autowired
    private BrokerService brokerService;
    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private OrderScheduler orderScheduler;
    @Autowired
    private BrokerSideUserService brokerSideUserService;

    public final static String DELAY_DISTRIBUTE = "DELAY_DISTRIBUTE";
    public final static String DELAY_ONE = "DELAY_ONE";
    public final static String INSTANT_DISTRIBUTE = "INSTANT_DISTRIBUTE";
    public final static String INSTANT_ONE = "INSTANT_ONE";

    public List<Broker> getBroker(String strategy, Integer brokerId){
        List<Broker> brokers = new ArrayList<>();
        switch (strategy){
            case DELAY_ONE:
            case INSTANT_ONE:
                brokers.add(brokerService.findById(brokerId));
                return brokers;
            case INSTANT_DISTRIBUTE:
            case DELAY_DISTRIBUTE:
                return brokerService.findAll();
            default:
                return null;
        }
    }

    public InstantSender create(String strategy){
        switch (strategy){
            case INSTANT_DISTRIBUTE:
                InstantDistributeSender s3 = new InstantDistributeSender(daoFactory, brokerSideUserService);
                return s3;

            case INSTANT_ONE:
                InstantOneSender s4 = new InstantOneSender(daoFactory, brokerSideUserService);
                return s4;

            default:
                InstantOneSender s5 = new InstantOneSender(daoFactory, brokerSideUserService);
                return s5;
        }
    }

    public Sender create(String strategy, Calendar startTime, Calendar endTime){
        switch (strategy){
            case DELAY_DISTRIBUTE:
                DelayDistributeSender s1 = new DelayDistributeSender(daoFactory, orderScheduler);
                s1.setStartTime(startTime);
                s1.setEndTime(endTime);
                return s1;

            case DELAY_ONE:
                DelayOneSender s2 = new DelayOneSender(daoFactory, orderScheduler);
                s2.setStartTime(startTime);
                s2.setEndTime(endTime);
                return s2;
            case INSTANT_DISTRIBUTE:
                return create(strategy);
            case INSTANT_ONE:
                return create(strategy);

            default:
                DelayOneSender s3 = new DelayOneSender(daoFactory, orderScheduler);
                s3.setStartTime(startTime);
                s3.setEndTime(endTime);
                return s3;
        }
    }
}
