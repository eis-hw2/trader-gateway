package com.example.trader.Core.Processor.Strategy;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Domain.Entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MeanProcessor implements Processor {
    private int slice;
    private static Logger logger = LoggerFactory.getLogger("MeanProcessor");

    public MeanProcessor(int slice){
        this.slice = slice;
    }

    @Override
    public List<Order> process(Order order) {
        logger.info("[MeanProcessor.process] " + JSON.toJSONString(order));

        int total = order.getTotalCount();
        int mean = total / slice;
        logger.info("[MeanProcessor.process] Slice: " + slice + " Mean: " + mean);

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < slice; i++){
            Order o = new Order(order);
            o.setTotalCount(mean);
            orders.add(o);
        }
        return orders;
    }
}
