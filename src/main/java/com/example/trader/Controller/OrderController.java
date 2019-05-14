package com.example.trader.Controller;

import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseWrapper create(
            @RequestBody Order order,
            @RequestParam(defaultValue = ProcessorFactory.NONE) String processStrategy,
            @RequestParam(defaultValue = SenderFactory.INSTANT) String sendStrategy,
            @RequestParam(defaultValue = "NONE") String brokerId) {
        try{
            List<Order> orders = orderService.create(order, processStrategy, sendStrategy, brokerId);
            return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
        }
        catch(Exception e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, e.getMessage());
        }

    }
}
