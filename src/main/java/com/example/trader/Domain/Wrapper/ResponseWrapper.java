package com.example.trader.Util.Wrapper;

import com.alibaba.fastjson.JSON;

public class ResponseWrapper {

    public static String SUCCESS = "success";
    public static String ERROR = "error";

    private String status;
    private Object body;

    public ResponseWrapper(){}

    public ResponseWrapper(String status, Object body){
        this.status = status;
        this.body = body;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
