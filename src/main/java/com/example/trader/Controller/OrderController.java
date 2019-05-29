package com.example.trader.Controller;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    private String getUsername(){
        return  SecurityContextHolder.getContext().getAuthentication().getName();

    }

    @PostMapping("")
    public ResponseWrapper createWithStrategy(
            @RequestBody Order order,
            @RequestParam(defaultValue = ProcessorFactory.NONE) String processStrategy,
            @RequestParam(defaultValue = SenderFactory.INSTANT) String sendStrategy,
            @RequestParam Integer brokerId) {
        System.out.println(JSON.toJSONString(order));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setTraderName(username);
        List<Order> orders = orderService.createWithStrategy(getUsername(), order, processStrategy, sendStrategy, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
        /*
        try{
            List<Order> orders = orderService.createWithStrategy(order, processStrategy, sendStrategy, brokerId);
            return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
        }
        catch(Exception e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, e.getMessage());
        }*/

    }

    @GetMapping("/MarketOrder")
    public ResponseWrapper getMarketOrder(@RequestParam Integer brokerId){
        List<Order> orders = orderService.findAll(Order.MARKET_ORDER, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
    }

    @GetMapping("/LimitOrder")
    public ResponseWrapper getLimitOrder(@RequestParam Integer brokerId){
        List<Order> orders = orderService.findAll(Order.LIMIT_ORDER, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
    }

    @GetMapping("/StopOrder")
    public ResponseWrapper getStopOrder(@RequestParam Integer brokerId){
        List<Order> orders = orderService.findAll(Order.STOP_ORDER, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
    }

    @GetMapping("/CancelOrder")
    public ResponseWrapper getCancelOrder(@RequestParam Integer brokerId){
        List<Order> orders = orderService.findAll(Order.CANCEL_ORDER, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
    }
}
