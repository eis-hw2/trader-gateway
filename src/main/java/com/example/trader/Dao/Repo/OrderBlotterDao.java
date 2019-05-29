package com.example.trader.Dao.Repo;

import com.alibaba.fastjson.JSONObject;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Util.DateUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("OrderBlotterDao")
@Scope("prototype")
public class OrderBlotterDao extends SecuredDao<String ,OrderBlotter>{

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

    public List<OrderBlotter> findByMarketDepthIdAndDate(String futureId, String date){
        // todo
        return null;
    }

    public List<OrderBlotter> findByMarketDepthIdAndInterval(String marketDepthId, String startTime, String endTime){
        String url = getBaseUrl() + "?marketDepthId=" + marketDepthId +
                "&startTime=" + startTime +
                "&endTime=" + endTime;
        ResponseEntity<OrderBlotter[]> responseEntity = getRestTemplate().getForEntity(url, OrderBlotter[].class);
        return Arrays.asList(responseEntity.getBody());

    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        System.out.print(DateUtil.dateFormat.format(calendar.getTime()));
    }
}
