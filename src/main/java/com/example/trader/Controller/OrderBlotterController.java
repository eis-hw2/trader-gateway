package com.example.trader.Controller;

import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Service.OrderBlotterService;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/v1/OrderBlotter")
public class OrderBlotterController {
    @Autowired
    OrderBlotterService orderBlotterService;

    @GetMapping("")
    public ResponseWrapper findAll(@RequestParam Integer brokerId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<OrderBlotter> res = orderBlotterService.findAll(username, brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    /*
    @GetMapping("/{obid}")
    public ResponseWrapper findById(@RequestParam Integer brokerId, @PathVariable String obid){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        OrderBlotter res = orderBlotterService.findById(username, brokerId, obid);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }*/

    @GetMapping("/query")
    public ResponseWrapper findByMarketDepthIdAndStartTimeAndEndTime(
            @RequestParam Integer brokerId,
            @RequestParam String marketDepthId,
            @RequestParam String startTime,
            @RequestParam String endTime){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<OrderBlotter> res = orderBlotterService.findByMarketDepthIdAndStartTimeAndEndTime(
                username, brokerId, marketDepthId, startTime, endTime
        );
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }
}
