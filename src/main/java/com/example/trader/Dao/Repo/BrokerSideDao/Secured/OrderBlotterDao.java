package com.example.trader.Dao.Repo.BrokerSideDao.Secured;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Dao.Repo.BrokerSideDao.SecuredDao;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component("OrderBlotterDao")
@Scope("prototype")
public class OrderBlotterDao extends SecuredDao<String ,OrderBlotter> {

    private static Logger logger = LoggerFactory.getLogger("OrderBlotterDao");

    @Override
    public Logger getLogger() {
        return logger;
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

        RestTemplate restTemplate = getRestTemplate();
        HttpEntity request = getHttpEntity();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, JSONObject.class);
        ResponseExtractor<ResponseEntity<JSONObject>> responseExtractor = restTemplate.responseEntityExtractor(JSONObject.class);
        ResponseEntity<JSONObject> responseEntity = restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);

        logger.info("[OrderBlotterDao.findByMarketDepthIdAndInterval] Result: "+ JSON.toJSONString(responseEntity.getBody()));
        OrderBlotter[] orderBlotters = responseEntity.getBody().getObject("body", OrderBlotter[].class);
        return new ArrayList<>(Arrays.asList(orderBlotters));
    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        System.out.print(DateUtil.dateFormat.format(calendar.getTime()));
    }
}
