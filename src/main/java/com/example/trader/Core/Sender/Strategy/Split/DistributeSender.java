package com.example.trader.Core.Sender.Strategy.Split;

import com.example.trader.Core.Sender.Strategy.SplitSender;
import com.example.trader.Domain.Order;
import com.example.trader.Core.Sender.Sender;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/*
* Send the Order to Different Broker
* */
@Component
public class DistributeSender extends SplitSender {
    @Override
    public ResponseWrapper send(List<Order> orders) {
        return null;
    }
}
