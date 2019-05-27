package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class DynamicDao<K, V> {
    private Logger logger = LoggerFactory.getLogger("Dao");
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
    public abstract Class<V> getValueClass();
    public abstract Class<V[]> getValueArrayClass();

    public V create(V  value){
        String url = getBroker().getWriteApi() + "/" + getType();
        logger.info("[Dao.create] " + url);
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().postForEntity(url, value, JSONObject.class);
        JSONObject rw = responseEntity.getBody();
        return null;
    }

    public V findById(String id) {
        String url = getBroker().getReadApi() + "/" + getType() + "/" + id;
        logger.info("[Dao.findById] " + url);
        V res = null;
        try {
            ResponseEntity<JSONObject> responseEntity = getRestTemplate().getForEntity(url, JSONObject.class);
            res = responseEntity.getBody().toJavaObject(getValueClass());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.info("[Dao.findById] " + JSON.toJSONString(res));
        return res;
    }

    public List<V> findAll(){
        String url = getBroker().getReadApi() + "/" + getType();
        logger.info("[Dao.findAll] " + url);
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().getForEntity(url, JSONObject.class);
        V[] res = responseEntity.getBody()
                .getJSONObject("_embedded")
                .getJSONArray(getType())
                .toJavaObject(getValueArrayClass());
        logger.info("[Dao.findAll] " + JSON.toJSONString(res));
        return Arrays.asList(res);
    }

    public static void main(String[] args){
        Broker broker= new Broker();
        broker.setUrl("pipipan.cn");
        FutureDao dao = new FutureDao();
        dao.setBroker(broker);
        System.out.println(JSON.toJSONString(dao.findAll().get(0).get__links()));
    }
}
