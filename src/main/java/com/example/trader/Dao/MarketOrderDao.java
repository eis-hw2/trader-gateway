package com.example.trader.Dao;

import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.Network.HttpRequest;
import com.example.trader.Util.Network.JsonHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MarketOrderDao extends Dao<String, Order>{

    public Order create(Order  order) {
        String url = getSource() + "/MarketOrder";
        String data = HttpRequest.requestWithBody(HttpRequest.METHOD_POST, order, url);
        ResponseWrapper res = JsonHelper.jsonStringToObject(data, ResponseWrapper.class);
        return (Order)res.getBody();
    }

    public Order modify(String id, Order order) {
        String url = getSource() + "/MarketOrder";
        String data = HttpRequest.requestWithBody(HttpRequest.METHOD_PUT, order, url);
        return null;
    }

    public void deleteById(String id) {
        String url = getSource() + "/MarketOrder/" + id;
        String data = HttpRequest.request(HttpRequest.METHOD_DELETE, url);
    }

    public Order getById(String  id) {
        String url = getSource() + "/MarketOrder/" + id;
        String data = HttpRequest.request(HttpRequest.METHOD_GET, url);
        return null;
    }
}
