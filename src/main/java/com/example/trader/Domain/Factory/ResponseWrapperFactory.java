package com.example.trader.Domain.Factory;

import com.example.trader.Domain.Wrapper.ResponseWrapper;

public class ResponseWrapperFactory {

    public static ResponseWrapper create(String status, Object detail){
        return new ResponseWrapper(status, detail);
    }

    public static String createResponseString(String status, Object detail){
        ResponseWrapper r = new ResponseWrapper(status, detail);
        return r.toString();
    }
}
