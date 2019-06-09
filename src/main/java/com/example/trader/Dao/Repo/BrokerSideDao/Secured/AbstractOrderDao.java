package com.example.trader.Dao.Repo.BrokerSideDao.Secured;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Dao.Repo.BrokerSideDao.SecuredDao;
import com.example.trader.Domain.Entity.Order;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractOrderDao extends SecuredDao<String, Order> {

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

        RestTemplate restTemplate = getRestTemplate();
        HttpEntity request = getHttpEntity();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, JSONObject.class);
        ResponseExtractor<ResponseEntity<JSONObject>> responseExtractor = restTemplate.responseEntityExtractor(JSONObject.class);
        ResponseEntity<JSONObject> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);


        Order[] res = responseEntity.getBody()
                .getJSONObject("_embedded")
                .getJSONArray(getType())
                .toJavaObject(getValueArrayClass());
        System.out.println(JSON.toJSONString(res));
        return new ArrayList<>(Arrays.asList(res));
    }

    @Override
    public Order create(Order  value){
        Logger logger = getLogger();
        String url = getWriteBaseUrl();
        logger.info("[OrderDao.create] URL: " + url);
        logger.info("[OrderDao.create] Order:" + JSON.toJSONString(value));
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().postForEntity(url, getHttpEntity(value), JSONObject.class);
        JSONObject rw = responseEntity.getBody();
        int status = rw.getInteger("status");
        logger.info("[OrderDao.create] Status: " + status);
        logger.info("[OrderDao.create] Response: " + rw.toJSONString());
        Order res;
        if (status != 200)
            res = Order.ERROR_ORDER;
        else
            res = rw.getObject("body", Order.class);
        return res;
    }
}
