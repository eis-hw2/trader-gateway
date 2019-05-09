package com.example.trader.Controller;

import com.example.trader.Service.BrokerService;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Broker;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/broker")
public class BrokerController {
    @Autowired
    BrokerService brokerConfigService;

    @PostMapping("")
    public ResponseWrapper addBroker(@RequestBody Broker broker){
        Broker res = brokerConfigService.addBroker(broker);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @GetMapping("")
    public ResponseWrapper getBroker(){
        List<Broker> res = brokerConfigService.getBroker();
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper deleteById(@PathVariable String id){
        boolean delete = brokerConfigService.deleteBrokerById(id);
        String status = delete ? ResponseWrapper.SUCCESS : ResponseWrapper.ERROR;
        String detail = delete ? "Delete Success" : "Delete Error";

        return ResponseWrapperFactory.create(status, detail);
    }
}
