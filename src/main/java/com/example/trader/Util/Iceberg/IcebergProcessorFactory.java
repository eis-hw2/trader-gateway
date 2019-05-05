package com.example.trader.Util.Iceberg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class IcebergProcessorFactory {
    @Autowired
    ApplicationContext applicationContext;

    private final static String VWAP = "VWAP";
    private final static String TWAP = "TWAP";

    public IcebergProcessor create(String name){
        switch (name){
            case VWAP:
                return applicationContext.getBean(VWAP, VWAP.class);
            case TWAP:
                return applicationContext.getBean(TWAP, TWAP.class);
            default:
                return null;
        }
    }
}
