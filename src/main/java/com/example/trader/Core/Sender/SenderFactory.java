package com.example.trader.Core.Sender;

import com.example.trader.Core.Sender.Strategy.Instant.InstantSender;
import com.example.trader.Core.Sender.Strategy.Delay.DistributeSender;
import com.example.trader.Core.Sender.Strategy.Delay.OneSender;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SenderFactory {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private BrokerService brokerService;

    public final static String DELAY_DISTRIBUTE = "DELAY_DISTRIBUTE";
    public final static String DELAY_ONE = "DELAY_ONE";
    public final static String INSTANT = "INSTANT";

    public List<Broker> getBroker(String strategy, Integer brokerId){
        List<Broker> brokers = new ArrayList<>();
        switch (strategy){
            case DELAY_ONE:
            case INSTANT:
                brokers.add(brokerService.findById(brokerId));
                return brokers;
            case DELAY_DISTRIBUTE:
                return brokerService.findAll();
            default:
                return null;
        }
    }

    public Sender create(String strategy){
        switch (strategy){
            case DELAY_DISTRIBUTE:
                DistributeSender distributeSender = applicationContext.getBean(DistributeSender.class);
                return distributeSender;

            case DELAY_ONE:
                OneSender oneSender = applicationContext.getBean(OneSender.class);
                return oneSender;

            case INSTANT:
                InstantSender instantSender = applicationContext.getBean(InstantSender.class);
                return instantSender;

            default:
                InstantSender sender = applicationContext.getBean(InstantSender.class);
                return sender;
        }
    }
}
