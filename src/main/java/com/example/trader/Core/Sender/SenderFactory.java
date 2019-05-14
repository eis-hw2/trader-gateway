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

    // TODO LRU
    private static ConcurrentHashMap<String, Sender> senders = new ConcurrentHashMap<>();

    public Sender create(String strategy, String brokerId, String type) throws Exception{
        String broker;
        if (brokerId.equals("NONE"))
            broker = brokerConfigService.getBroker().get(0).getUrl();
        else {
            broker = brokerConfigService.getBrokerById(brokerId).getUrl();
            if (broker == null)
                throw new Exception("Broker Not Exist");
        }

        switch (strategy){
            case SPLIT_DISTRIBUTE:
                if (senders.containsKey(SPLIT_DISTRIBUTE))
                    return senders.get(SPLIT_DISTRIBUTE);

                DistributeSender distributeSender = applicationContext.getBean(DistributeSender.class);
                senders.put(SPLIT_DISTRIBUTE, distributeSender);
                return distributeSender;

            case SPLIT_ONE:
                String k1 = SPLIT_ONE + brokerId + "/" + type;
                if (senders.containsKey(k1))
                    return senders.get(k1);

                OneSender oneSender = applicationContext.getBean(OneSender.class);

                oneSender.setBroker(broker + "/" + type);
                senders.put(k1, oneSender);
                return oneSender;

            case INSTANT:
                String k2 = INSTANT + brokerId + "/" + type;
                if (senders.containsKey(k2))
                    return senders.get(strategy);
                InstantSender instantSender = applicationContext.getBean(InstantSender.class);

                instantSender.setBroker(broker + "/" + type);
                senders.put(k2, instantSender);
                return instantSender;

            default:
                String k3 = INSTANT + brokerId + "/" + type;
                if (senders.containsKey(k3))
                    return senders.get(strategy);
                InstantSender sender = applicationContext.getBean(InstantSender.class);

                sender.setBroker(broker + "/" + type);
                senders.put(k3, sender);
                return sender;
        }
    }
}
