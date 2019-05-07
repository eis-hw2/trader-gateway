package com.example.trader.Util;

import com.example.trader.Util.Wrapper.ResponseWrapper;

public class ResponseWrapperFactory {
    public static ResponseWrapper create() {
        return new ResponseWrapper();
    }

    public static ResponseWrapper create(String status, Object detail){
        return new ResponseWrapper(status, detail);
    }

    public static String createResponseString(String status, Object detail){
        ResponseWrapper r = new ResponseWrapper(status, detail);
        return r.toString();
    }
}
