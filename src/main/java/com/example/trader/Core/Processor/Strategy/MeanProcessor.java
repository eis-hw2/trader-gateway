package com.example.trader.Core.Processor.Strategy;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MeanProcessor extends Processor {
    private int slice;
    private static Logger logger = LoggerFactory.getLogger("MeanProcessor");

    public MeanProcessor(int slice){
        this.slice = slice;
    }

    @Override
    public List<Order> process(Order order) {
        logger.info("[MeanProcessor.process] " + JSON.toJSONString(order));

        int total = order.getCount();
        int mean = total / slice;
        logger.info("[MeanProcessor.process] Slice: " + slice + " Mean: " + mean);

        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < slice; i++){
            Order o = new Order(order);
            o.setCount(mean);
            orders.add(o);
        }
        return orders;
    }
}
