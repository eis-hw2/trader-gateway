package com.example.trader.Controller;

import com.example.trader.Service.BrokerConfigService;
import com.example.trader.Util.ResponseWrapperFactory;
import com.example.trader.Util.Wrapper.BrokerWrapper;
import com.example.trader.Util.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/broker")
public class BrokerController {
    @Autowired
    BrokerConfigService brokerConfigService;

    @PostMapping("")
    public ResponseWrapper addBroker(@RequestBody BrokerWrapper brokerWrapper){
        BrokerWrapper res = brokerConfigService.addBroker(brokerWrapper);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @GetMapping("")
    public ResponseWrapper getBroker(){
        List<BrokerWrapper> res = brokerConfigService.getBroker();
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper deleteById(@PathVariable Integer id){
        boolean delete = brokerConfigService.deleteBrokerById(id);
        String status = delete ? ResponseWrapper.SUCCESS : ResponseWrapper.ERROR;
        String detail = delete ? "Delete Success" : "Delete Error";

        return ResponseWrapperFactory.create(status, detail);
    }
}
