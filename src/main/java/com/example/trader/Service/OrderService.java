package com.example.trader.Service;

import com.example.trader.Entity.Order;
import com.example.trader.Util.Wrapper.ResponseWrapper;

import java.util.List;

public interface OrderService {

    Order getById(String id);

    Order create(Order order, boolean iceberg);
}
