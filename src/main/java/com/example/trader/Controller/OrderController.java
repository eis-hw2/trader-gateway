package com.example.trader.Controller;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.OrderService;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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
            @RequestParam(defaultValue = DateUtil.TOMMOROW_OPEN) String openTime,
            @RequestParam(defaultValue = DateUtil.TOMMOROW_CLOSE) String closeTime,
            @RequestParam Integer brokerId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setTraderName(username);

        Object res;
        if (processStrategy.equals(ProcessorFactory.NONE)) {
            res = orderService.create(username, order, brokerId);
        }
        else{
            try {
                res = orderService.createWithStrategy(getUsername(),
                        order, processStrategy, sendStrategy, brokerId,
                        openTime, closeTime);
            }
            catch(ParseException e){
                return ResponseWrapperFactory.create(ResponseWrapper.ERROR,
                        "Time format should be yyyy-mm-dd HH:mm:ss");
            }
        }
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
        /*
        try{
            List<Order> orders = orderService.createWithStrategy(order, processStrategy, sendStrategy, brokerId);
            return ResponseWrapperFactory.createWithToken(ResponseWrapper.SUCCESS, orders);
        }
        catch(Exception e){
            return ResponseWrapperFactory.createWithToken(ResponseWrapper.ERROR, e.getMessage());
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
