package com.example.trader.Util.Wrapper;

import com.alibaba.fastjson.JSON;

public class ResponseWrapper {
    private String status;
    private Object detail;

    public ResponseWrapper(){}

    public ResponseWrapper(String status, Object detail){
        this.status = status;
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
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
