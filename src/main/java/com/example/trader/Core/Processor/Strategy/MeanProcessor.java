package com.example.trader.Core.Processor.Strategy;

import com.example.trader.Core.Processor.Processor;
import com.example.trader.Domain.Entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MeanProcessor extends Processor{

    private final int DEFAULT_NUMBER = 10;
    private int slice = DEFAULT_NUMBER;

    @Override
    public List<Order> process(Order order) {
        int total = order.getCount();
        int mean = total / getSlice();
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < getSlice(); i++){
            Order o = new Order(order);
            o.setCount(mean);
            orders.add(o);
        }
        return orders;
    }

    public int getSlice() {
        return slice;
    }

    public void setSlice(int slice) {
        this.slice = slice;
    }
}
