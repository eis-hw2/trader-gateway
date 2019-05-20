package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class DynamicDao<K, V> {
    private RestTemplate restTemplate = new RestTemplate();

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    private Broker broker;

    public Broker getBroker() {
        return broker;
    }
    public void setBroker(Broker broker) {
        this.broker = broker;
    }

    public abstract String getType();

    public V create(V  value){
        String url = getBroker().getWriteApi() + getType();
        ResponseEntity<ResponseWrapper> responseEntity = getRestTemplate().postForEntity(url, value, ResponseWrapper.class);
        ResponseWrapper rw = responseEntity.getBody();
        System.out.println(JSON.toJSONString(rw));
        return null;
    }
}
