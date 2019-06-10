package com.example.trader.Service;

import com.example.trader.Domain.Entity.OrderBlotter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

public interface OrderBlotterService {
    List<OrderBlotter> findAll(String usernname, Integer brokerId);
    OrderBlotter findById(String usernname, Integer brokerId, String id);
    List<OrderBlotter> findByMarketDepthIdAndStartTimeAndEndTime(String traderSideUsername, Integer brokerId, String marketDepthId, String startTime, String endTime);
}
