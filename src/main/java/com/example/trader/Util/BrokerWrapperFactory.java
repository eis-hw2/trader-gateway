package com.example.trader.Util;

import com.example.trader.Util.Wrapper.BrokerWrapper;

public class BrokerWrapperFactory {
    public static BrokerWrapper create(String url){
        return new BrokerWrapper(url);
    }
}
