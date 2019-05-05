package com.example.trader.Service;

import com.example.trader.Entity.Order;
import com.example.trader.Util.Wrapper.ResponseWrapper;

import java.util.List;

public interface OrderService {

    short STRATEGY_NONE = 0;
    short STRATEGY_VWAP = 1;
    short STRATEGY_TWAP = 2;

    Order getById(String id);

    Order create(Order order, int strategy);
}
