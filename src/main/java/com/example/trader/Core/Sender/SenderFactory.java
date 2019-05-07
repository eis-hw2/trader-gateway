package com.example.trader.Core.Sender;

import com.example.trader.Core.Sender.Strategy.DistributeSender;
import com.example.trader.Core.Sender.Strategy.NoneSender;
import com.example.trader.Service.BrokerService;
import com.example.trader.Domain.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SenderFactory {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private BrokerService brokerConfigService;

    private final static String DISTRIBUTE = "DISTRIBUTE";

    public Sender create(String strategy){
        switch (strategy){
            case DISTRIBUTE:
                return applicationContext.getBean(DistributeSender.class);

            default:
                NoneSender noneSender = applicationContext.getBean(NoneSender.class);

                Broker broker = brokerConfigService.getBrokerById(strategy);
                noneSender.setBroker(broker.getUrl());

                return noneSender;
        }
    }
}
