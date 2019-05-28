package com.example.trader.Core.Processor;

import com.example.trader.Core.Processor.Strategy.TwapProcessor;
import com.example.trader.Core.Processor.Strategy.NoneProcessor;
import com.example.trader.Core.Processor.Strategy.VwapProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProcessorFactory {
    @Autowired
    ApplicationContext applicationContext;

    public final static String VWAP = "VWAP";
    public final static String TWAP = "TWAP";
    public final static String NONE = "NONE";

    public Processor create(String name){
        switch (name){
            case VWAP:
                return applicationContext.getBean(VwapProcessor.class);
            case TWAP:
                return applicationContext.getBean(TwapProcessor.class);
            default:
                return applicationContext.getBean(NoneProcessor.class);
        }
    }
}
