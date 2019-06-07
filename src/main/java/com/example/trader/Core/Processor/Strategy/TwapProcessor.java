package com.example.trader.Core.Processor.Strategy;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TwapProcessor implements Processor{

    private final static Logger logger = LoggerFactory.getLogger("TwapProcessor");

    /**
     * Minute
     */
    private int interval;

    private Calendar startTime;
    private Calendar endTime;

    public TwapProcessor(Calendar startTime, Calendar endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TwapProcessor(){}

    @Override
    public List<Order> process(Order order) {
        logger.info("[TwapProcessor] startTime: " + DateUtil.datetimeFormat.format(startTime.getTime()));
        logger.info("[TwapProcessor] endTime: " + DateUtil.datetimeFormat.format(endTime.getTime()));
        logger.info("[TwapProcessor.process] " + JSON.toJSONString(order));

        int total = order.getTotalCount();
        int slice = DateUtil.getMinuteInterval(startTime, endTime) / interval;
        int mean = total / slice;
        logger.info("[TwapProcessor.process] Slice: " + slice + " Mean: " + mean);

        List<Order> orders = new ArrayList<>();

        int gap = total - mean * slice;

        for (int i = 0; i < slice; i++){
            Order o = new Order(order);
            o.setTotalCount(mean);
            orders.add(o);
        }
        Order temp = orders.get(0);
        temp.setTotalCount(temp.getTotalCount() + gap);
        return orders;
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

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
