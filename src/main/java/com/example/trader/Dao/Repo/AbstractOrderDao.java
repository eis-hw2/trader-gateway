package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractOrderDao extends DynamicDao<String, Order>{

    public List<Order> findAll(){
        String url = getBroker().getReadApi() + getType();
        ResponseEntity<Order[]> responseEntity = getRestTemplate().getForEntity(url, Order[].class);
        List<Order> res = Arrays.asList(responseEntity.getBody());
        System.out.println(JSON.toJSONString(res));
        return res;
    }

    public Order modify(String id, Order order) {
        String url = getBroker().getWriteApi() + getType();
        return null;
    }

    public void deleteById(String id) {
        String url = getBroker().getWriteApi() + getType() + "/" + id;
    }

    public Order getById(String  id) {
        String url = getBroker().getReadApi() + getType() + "/" + id;
        return null;
    }
}
