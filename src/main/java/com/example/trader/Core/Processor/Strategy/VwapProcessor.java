package com.example.trader.Core.Processor.Strategy;

import com.example.trader.Dao.Impl.OrderBlotterDao;
import com.example.trader.Domain.Order;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Core.Processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VwapProcessor extends Processor {
    @Autowired
    OrderBlotterDao orderBlotterDao;

    @Override
    public List<Order> process(Order order){
        List<OrderBlotter> orderBlotters = getYesterdayOrderBlotter(order.getMarketDepthId());
        double[] percents = preprocess(orderBlotters);
        List<Order> splitOrder = new ArrayList<>(); // 24 hour

        int count = order.getCount();
        int splitSum = 0;

        for (int i = 0; i < 24; i++){
            int splitCnt = (int)Math.floor(percents[i] * count);
            splitSum += splitCnt;
            Order split = makeSplitOrder(order, splitCnt);
            splitOrder.add(split);
        }
        if (splitSum < count){
            Order first = splitOrder.get(0);
            first.setCount(first.getCount() + (count - splitSum));
        }
        return splitOrder;
    }

    private List<OrderBlotter> getYesterdayOrderBlotter(String futureId){
        return orderBlotterDao.getByFutureIdYesterday(futureId);
    }

    private Order makeSplitOrder(Order origin, int count){
        Order split = new Order(origin);
        split.setCount(count);
        return split;
    }

    /*
     * 计算每小时的成交量占该日成交额的百分比
     */
    private double[] preprocess(List<OrderBlotter> orderBlotters){
        return null;
    }
}
