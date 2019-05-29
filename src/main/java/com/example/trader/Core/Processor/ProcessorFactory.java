package com.example.trader.Core.Processor;

import com.example.trader.Core.Processor.Strategy.TwapProcessor;
import com.example.trader.Core.Processor.Strategy.NoneProcessor;
import com.example.trader.Core.Processor.Strategy.VwapProcessor;
import com.example.trader.Dao.Repo.OrderBlotterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class ProcessorFactory {
    @Autowired
    OrderBlotterDao orderBlotterDao;

    public final static String VWAP = "VWAP";
    public final static String TWAP = "TWAP";
    public final static String NONE = "NONE";

    public Processor create(String name, Calendar startTime, Calendar endTime){
        Processor p;
        switch (name){
            case VWAP:
                p = new VwapProcessor(startTime, endTime, orderBlotterDao);
                break;
            case TWAP:
                p = new TwapProcessor(startTime, endTime);
                break;
            default:
                p = new NoneProcessor();
                break;
        }
        return p;

    }
}
