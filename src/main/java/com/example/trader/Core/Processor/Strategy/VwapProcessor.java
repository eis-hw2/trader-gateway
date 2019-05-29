package com.example.trader.Core.Processor.Strategy;

import com.example.trader.Dao.Repo.OrderBlotterDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class VwapProcessor extends Processor {

    private OrderBlotterDao orderBlotterDao;
    private Calendar startTime;
    private Calendar endTime;
    /**
     * Minute
     */
    private final int DEFAULT_INTERVAL = 5;

    public VwapProcessor(Calendar startTime, Calendar endTime, OrderBlotterDao od){
        this.startTime = startTime;
        this.endTime = endTime;
        this.orderBlotterDao = od;
    }

    @Override
    public List<Order> process(Order order){

        String start = DateUtil.datetimeFormat.format(startTime);
        String end = DateUtil.datetimeFormat.format(endTime);
        List<OrderBlotter> orderBlotters = orderBlotterDao.findByMarketDepthIdAndInterval(
                order.getMarketDepthId(),
                start, end);


        double[] percents = getPercentage(orderBlotters);

        List<Order> splitOrder = new ArrayList<>(percents.length);

        int sum = order.getCount();
        int splitSum = 0;

        for (int i = 0; i < percents.length; i++){
            int splitCnt = (int)Math.floor(percents[i] * sum);
            splitSum += splitCnt;
            Order split = makeSplitOrder(order, splitCnt);
            splitOrder.add(split);
        }
        if (splitSum < sum){
            Order first = splitOrder.get(0);
            first.setCount(first.getCount() + (sum - splitSum));
        }
        return splitOrder;
    }


    private Order makeSplitOrder(Order origin, int count){
        Order split = new Order(origin);
        split.setCount(count);
        return split;
    }

    /**
     * 0          30           60         90
     * |-----------|-----------|-----------|
     *   index:0     index:1       index:2
     */
    private int getIndex(Calendar target){
        int minutes = DateUtil.getMinuteInterval(startTime, target);
        int index = (int) Math.floor( (double)minutes / DEFAULT_INTERVAL );
        return index;
    }

    /**
     * 计算每个 DEFAULT_INTERVAL 内成交额占该时间段的成交额的百分比
     */
    private double[] getPercentage(List<OrderBlotter> orderBlotters){
        int slice = getIndex(endTime);

        double[] res = new double[slice];

        Long sum = orderBlotters.stream().parallel().reduce( new Long(0), (u, s) -> {
            u = u + s.getCount();
            return u;
        }, (cnt, cnt2) -> cnt + cnt2);

        orderBlotters.sort(Comparator.comparing(OrderBlotter::getCreationTime));

        orderBlotters.stream().forEach(ob -> {
            try{
                Calendar createdTime = DateUtil.stringToCalendar(ob.getCreationTime(), DateUtil.datetimeFormat);
                int index = getIndex(createdTime);
                res[index] += ob.getCount();
            }
            catch(ParseException e){}
        });

        for (int i = 0; i < slice; ++i){
            res[i] = res[i] / sum;
        }
        return res;
    }

    public OrderBlotterDao getOrderBlotterDao() {
        return orderBlotterDao;
    }

    public void setOrderBlotterDao(OrderBlotterDao orderBlotterDao) {
        this.orderBlotterDao = orderBlotterDao;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
