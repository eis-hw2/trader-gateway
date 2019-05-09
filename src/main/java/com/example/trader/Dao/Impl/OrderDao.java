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
        String data = HttpRequest.requestWithBody(HttpRequest.METHOD_POST, order, getSource() + "/Order");
        ResponseWrapper res = JsonHelper.jsonStringToObject(data, ResponseWrapper.class);
        return (Order)res.getBody();
    }

    @Override
    public Order modify(Order order) {
        return null;
    }

    @Override
    public Order deleteById(String id) {
        return null;
    }

    @Override
    public Order getById(String  id) {
        return null;
    }
}
