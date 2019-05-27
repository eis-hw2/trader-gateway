package com.example.trader.Service;

import com.example.trader.Domain.Entity.Future;

import java.util.List;

public interface FutureService {
    List<Future> findAll(Integer brokerId);
    Future findById(Integer brokerId, String id);
}
