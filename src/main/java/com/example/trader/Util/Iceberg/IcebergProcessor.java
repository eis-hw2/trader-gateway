package com.example.trader.Util.Iceberg;

import com.example.trader.Entity.Order;

import java.util.List;

public abstract class IcebergProcessor {
    public abstract List<Order> process(Order order);
}
