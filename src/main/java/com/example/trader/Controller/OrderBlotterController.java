package com.example.trader.Controller;

import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Service.OrderBlotterService;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/OrderBlotter")
public class OrderBlotterController {
    @Autowired
    OrderBlotterService orderBlotterService;

    @GetMapping("")
    public ResponseWrapper findAll(@RequestParam Integer brokerId){
        List<OrderBlotter> res = orderBlotterService.findAll(brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @GetMapping("/{obid}")
    public ResponseWrapper findById(@RequestParam Integer brokerId, @PathVariable String obid){
        OrderBlotter res = orderBlotterService.findById(brokerId, obid);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }
}
