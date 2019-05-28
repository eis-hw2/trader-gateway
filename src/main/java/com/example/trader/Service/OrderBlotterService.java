package com.example.trader.Service;

import com.example.trader.Domain.Entity.OrderBlotter;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

public interface OrderBlotterService {
    List<OrderBlotter> findAll(Integer brokerId);
    OrderBlotter findById(Integer brokerId, String id);
    List<OrderBlotter> findByFutureIdAndTime(Integer brokerId, String futureId, Calendar startTime, Calendar endTime);

}