package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("OrderBlotterDao")
@Scope("prototype")
public class OrderBlotterDao extends SecuredDao<String ,OrderBlotter>{

    private static Logger logger = LoggerFactory.getLogger("OrderBlotterDao");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
        return headers;
    }

    @Override
    public String getType() {
        return "orderBlotters";
    }

    @Override
    public Class<OrderBlotter> getValueClass() {
        return OrderBlotter.class;
    }

    @Override
    public Class<OrderBlotter[]> getValueArrayClass() {
        return OrderBlotter[].class;
    }

    // todo
    public List<OrderBlotter> findByMarketDepthId(String mkid){
        return null;
    }

    public List<OrderBlotter> findByMarketDepthIdAndInterval(String marketDepthId, String startTime, String endTime){

        String url = getReadBaseUrl() + "?marketDepthId=" + marketDepthId +
                "&startTime=" + startTime +
                "&endTime=" + endTime;
        logger.info("[OrderBlotterDao.findByMarketDepthIdAndInterval] URL: "+url);
        ResponseEntity<JSONObject> responseEntity = getRestTemplate().getForEntity(url, JSONObject.class);
        logger.info("[OrderBlotterDao.findByMarketDepthIdAndInterval] Result: "+ JSON.toJSONString(responseEntity.getBody()));
        OrderBlotter[] orderBlotters = responseEntity.getBody().getObject("body", OrderBlotter[].class);
        return Arrays.asList(orderBlotters);
    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        System.out.print(DateUtil.dateFormat.format(calendar.getTime()));
    }
}
