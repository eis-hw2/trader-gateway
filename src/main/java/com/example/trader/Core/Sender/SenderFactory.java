package com.example.trader.Core.Sender;

import com.example.trader.Core.Sender.Strategy.Instant.InstantSender;
import com.example.trader.Core.Sender.Strategy.Split.DistributeSender;
import com.example.trader.Core.Sender.Strategy.Split.OneSender;
import com.example.trader.Service.BrokerService;
import com.example.trader.Domain.Broker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class SenderFactory {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private BrokerService brokerConfigService;

    private final static String SPLIT_DISTRIBUTE = "SPLIT_DISTRIBUTE";
    private final static String SPLIT_ONE = "SPLIT_ONE";
    private final static String INSTANT = "INSTANT";

    private static ConcurrentHashMap<String, Sender> senders = new ConcurrentHashMap<>();

    public Sender create(String strategy, String brokerId){
        switch (strategy){
            case SPLIT_DISTRIBUTE:
                if (senders.containsKey(SPLIT_DISTRIBUTE))
                    return senders.get(SPLIT_DISTRIBUTE);

                DistributeSender distributeSender = applicationContext.getBean(DistributeSender.class);
                senders.put(SPLIT_DISTRIBUTE, distributeSender);
                return distributeSender;

            case SPLIT_ONE:
                String k1 = SPLIT_ONE + brokerId;
                if (senders.containsKey(k1))
                    return senders.get(k1);

                OneSender oneSender = applicationContext.getBean(OneSender.class);
                oneSender.setBroker(brokerId);
                senders.put(k1, oneSender);
                return oneSender;

            case INSTANT:
                String k2 = INSTANT + brokerId;
                if (senders.containsKey(k2))
                    return senders.get(strategy);
                InstantSender instantSender = applicationContext.getBean(InstantSender.class);
                Broker bro = brokerConfigService.getBrokerById(brokerId);
                instantSender.setBroker(bro.getUrl());
                senders.put(k2, instantSender);
                return instantSender;

            default:
                String k3 = INSTANT + brokerId;
                if (senders.containsKey(k3))
                    return senders.get(strategy);
                InstantSender sender = applicationContext.getBean(InstantSender.class);
                Broker b = brokerConfigService.getBrokerById(strategy);
                sender.setBroker(b.getUrl());
                senders.put(k3, sender);
                return sender;
        }
    }
}
