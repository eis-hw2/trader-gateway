package com.example.trader.Core.Sender;

import com.example.trader.Core.Sender.Strategy.Instant.InstantSender;
import com.example.trader.Core.Sender.Strategy.Split.DistributeSender;
import com.example.trader.Core.Sender.Strategy.Split.OneSender;
import com.example.trader.Service.BrokerService;
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

    public final static String SPLIT_DISTRIBUTE = "SPLIT_DISTRIBUTE";
    public final static String SPLIT_ONE = "SPLIT_ONE";
    public final static String INSTANT = "INSTANT";

    public Sender create(String strategy, Integer brokerId, String type) throws Exception{
        String broker;
        if (brokerId.equals(-1))
            broker = brokerConfigService.getBroker().get(0).getUrl();
        else {
            broker = brokerConfigService.getBrokerById(brokerId).getUrl();
            if (broker == null)
                throw new Exception("Broker Not Exist");
        }

        switch (strategy){
            case SPLIT_DISTRIBUTE:
                DistributeSender distributeSender = applicationContext.getBean(DistributeSender.class);
                return distributeSender;

            case SPLIT_ONE:
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
