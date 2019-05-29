package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Core.Sender.Sender;

import java.util.Calendar;

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
}
