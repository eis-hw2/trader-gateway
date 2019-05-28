package com.example.trader.Controller;

import com.example.trader.Service.BrokerService;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Broker")
public class BrokerController {
    @Autowired
    BrokerService brokerService;

    @GetMapping("")
    public ResponseWrapper findAll(){
        List<Broker> res = brokerService.findAll();
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }
}
