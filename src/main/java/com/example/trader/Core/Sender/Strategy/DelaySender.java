package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;

import java.util.Calendar;
import java.util.List;

public abstract class DelaySender extends Sender {
    private Calendar startTime;
    private Calendar endTime;

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

    /**
     *
     * @param traderSideUsername
     * @param brokers
     * @param orders
     * @return FutureTask ID
     */
    @Override
    public abstract Integer send(String traderSideUsername, List<Broker> brokers, List<Order> orders);

}
