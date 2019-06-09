package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Dao.Repo.TraderSideDao.OrderToSendDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

import java.util.Calendar;
import java.util.List;

public abstract class DelaySender implements Sender {
    private List<Broker> brokers;
    private Calendar startTime;
    private Calendar endTime;

    private int intervalMinute;

    private OrderToSendDao orderToSendDao;

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

    public List<Broker> getBrokers() {
        return brokers;
    }

    public void setBrokers(List<Broker> brokers) {
        this.brokers = brokers;
    }

    /**
     *
     * @param traderSideUsername
     * @param orders
     * @return FutureTask ID
     */
    @Override
    public abstract String send(String traderSideUsername, List<Order> orders);

    public int getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(int intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public OrderToSendDao getOrderToSendDao() {
        return orderToSendDao;
    }

    public void setOrderToSendDao(OrderToSendDao orderToSendDao) {
        this.orderToSendDao = orderToSendDao;
    }

    public boolean checkOrder(Order order){
        if (order.getType().equals("CancelOrder"))
            return false;
        return true;
    }
}
