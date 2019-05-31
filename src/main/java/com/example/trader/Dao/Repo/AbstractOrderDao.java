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

public abstract class AbstractOrderDao extends SecuredDao<String, Order>{

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
        return headers;
    }

    @Override
    public Class<Order> getValueClass() {
        return Order.class;
    }

    @Override
    public Class<Order[]> getValueArrayClass() {
        return Order[].class;
    }

    public List<Order> findByTraderName(String traderName){
        String url = getReadBaseUrl() + "/search/traderName?traderName=" + traderName;
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().getForEntity(url, JSONObject.class);
        Order[] res = responseEntity.getBody()
                .getJSONObject("_embedded")
                .getJSONArray(getType())
                .toJavaObject(getValueArrayClass());
        System.out.println(JSON.toJSONString(res));
        return Arrays.asList(res);
    }

    @Override
    public Order create(Order  value){
        String url = getWriteBaseUrl();
        this.getLogger().info("[OrderDao.create] " + url);
        this.getLogger().info("[OrderDao.create] " + JSON.toJSONString(value));
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().postForEntity(url, getHttpEntity(value), JSONObject.class);
        JSONObject rw = responseEntity.getBody();
        int status = rw.getInteger("status");
        this.getLogger().info("[OrderDao.create] " + status);
        this.getLogger().info("[OrderDao.create] " + rw.toJSONString());
        Order res;
        if (status != 200)
            res = Order.ERROR_ORDER;
        else
            res = rw.getObject("body", Order.class);
        return res;
    }
}
