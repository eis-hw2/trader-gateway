package com.example.trader.Controller;

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
            @RequestParam(defaultValue = SenderFactory.INSTANT_ONE) String sendStrategy,
            @RequestParam(defaultValue = DateUtil.TOMMOROW_OPEN) String startTime,
            @RequestParam(defaultValue = DateUtil.TOMMOROW_CLOSE) String endTime,
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
                        startTime, endTime);
            }
            catch(ParseException e){
                return ResponseWrapperFactory.create(ResponseWrapper.ERROR,
                        "Time format should be yyyy-mm-dd HH:mm:ss");
            }
        }
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @GetMapping("")
    public ResponseWrapper getOrderById(
            @RequestParam String type,
            @RequestParam String orderId,
            @RequestParam Integer brokerId){
        Order order = orderService.findById(type, orderId, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, order);
    }
}
