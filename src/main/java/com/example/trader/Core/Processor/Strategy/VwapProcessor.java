package com.example.trader.Core.Processor.Strategy;

import com.alibaba.fastjson.JSON;
import com.example.trader.Dao.Repo.BrokerSideDao.Secured.OrderBlotterDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Core.Processor.Processor;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class VwapProcessor implements Processor {

    private OrderBlotterDao orderBlotterDao;
    private Calendar startTime;
    private Calendar endTime;

    private static final Logger logger = LoggerFactory.getLogger("VwapProcessor");
    /**
     * Minute
     */
    private Integer intervalMinute;
    private static final int DEFAULT_INTERVAL = 5;

    public VwapProcessor(Calendar startTime, Calendar endTime, OrderBlotterDao od, Integer intervalMinute){
        this.orderBlotterDao = od;
        this.intervalMinute = intervalMinute;

        /**
         * 根据昨天该时间段的 OrderBlotter 进行划分
         * 未来可以添加其他种类的 VWAP 策略，比如根据过去三天等等
         */
        Calendar startSearchCalendar = (Calendar) startTime.clone();
        startSearchCalendar.add(Calendar.DAY_OF_MONTH, -1);
        this.startTime = startSearchCalendar;

        Calendar endSearchCalendar = (Calendar) endTime.clone();
        endSearchCalendar.add(Calendar.DAY_OF_MONTH, -1);
        this.endTime = endSearchCalendar;
    }

    @Override
    public List<Order> process(Order order){

        logger.info("[VwapProcessor.process] StartTime: " + DateUtil.calendarToString(startTime, DateUtil.datetimeFormat));
        logger.info("[VwapProcessor.process] EndTime: " + DateUtil.calendarToString(endTime, DateUtil.datetimeFormat));
        logger.info("[VwapProcessor.process] Order: " + JSON.toJSONString(order));

        String startSearch = DateUtil.calendarToString(startTime, DateUtil.datetimeFormat);
        String endSearch = DateUtil.calendarToString(endTime, DateUtil.datetimeFormat);

        List<OrderBlotter> orderBlotters = orderBlotterDao.findByMarketDepthIdAndInterval(
                order.getMarketDepthId(),
                startSearch, endSearch);


        double[] percents = getPercentage(orderBlotters);

        List<Order> splitOrder = new ArrayList<>(percents.length);

        int sum = order.getTotalCount();
        int splitSum = 0;

        for (int i = 0; i < percents.length; i++){
            int splitCnt = (int)Math.floor(percents[i] * sum);
            splitSum += splitCnt;
            Order split = makeSplitOrder(order, splitCnt);
            splitOrder.add(split);
        }
        if (splitSum < sum){
            Order first = splitOrder.get(0);
            first.setTotalCount(first.getTotalCount() + (sum - splitSum));
        }
        logger.info("[VwapProcessor.process] Result: " + JSON.toJSONString(splitOrder));
        return splitOrder;
    }


    private Order makeSplitOrder(Order origin, int count){
        Order split = new Order(origin);
        split.setTotalCount(count);
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
                logger.debug("[VwapProcessor.getPercentage] Created Time: " + ob.getCreationTime());
                Calendar createdTime = DateUtil.stringToCalendar(ob.getCreationTime(), DateUtil.datetimeFormat);
                int index = getIndex(createdTime);
                logger.debug("[VwapProcessor.getPercentage] Index: " + index );
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

    public int getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        this.intervalMinute = intervalMinute;
    }
}
