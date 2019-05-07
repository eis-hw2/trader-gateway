package com.example.trader.Core.Sender.Strategy;

import com.example.trader.Domain.Order;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DistributeSender extends Sender{
    @Override
    public ResponseWrapper send(List<Order> orders) {
        return null;
    }
}
