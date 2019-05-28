package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractOrderDao extends DynamicDao<String, Order>{

    private String token;

    @Override
    public Class<Order> getValueClass() {
        return Order.class;
    }

    @Override
    public Class<Order[]> getValueArrayClass() {
        return Order[].class;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
        return headers;
    }

    public List<Order> findByTraderName(String traderName){
        String url = getBroker().getReadApi() + "/" + getType() + "/search/traderName?traderName=" + traderName;
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().getForEntity(url, JSONObject.class);
        Order[] res = responseEntity.getBody()
                .getJSONObject("_embedded")
                .getJSONArray(getType())
                .toJavaObject(getValueArrayClass());
        System.out.println(JSON.toJSONString(res));
        return Arrays.asList(res);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
