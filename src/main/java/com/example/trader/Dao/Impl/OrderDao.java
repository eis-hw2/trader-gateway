package com.example.trader.Dao.Impl;

import com.example.trader.Dao.Dao;
import com.example.trader.Domain.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.Network.HttpRequest;
import com.example.trader.Util.Network.JsonHelper;

import java.util.List;

public class OrderDao extends Dao<String, Order> {
    @Override
    public Order create(Order  order) {
        String url = getSource() + "/Order";
        String data = HttpRequest.requestWithBody(HttpRequest.METHOD_POST, order, url);
        ResponseWrapper res = JsonHelper.jsonStringToObject(data, ResponseWrapper.class);
        return (Order)res.getBody();
    }

    @Override
    public Order modify(String id, Order order) {
        String url = getSource() + "/Order/" + id;
        String data = HttpRequest.requestWithBody(HttpRequest.METHOD_PUT, order, url);
        return null;
    }

    @Override
    public Order deleteById(String id) {
        String url = getSource() + "/Order/" + id;
        String data = HttpRequest.request(HttpRequest.METHOD_DELETE, url);
        return null;
    }

    @Override
    public Order getById(String  id) {
        String url = getSource() + "/Order/" + id;
        String data = HttpRequest.request(HttpRequest.METHOD_GET, url);
        return null;
    }
}
