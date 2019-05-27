package com.example.trader.Controller;

import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/BrokerSideUser")
public class BrokerSideUserController {
    @Autowired
    private BrokerSideUserService brokerSideUserService;

    @GetMapping("/test")
    public ResponseWrapper test(){
        String token = brokerSideUserService.login("zzbslayer", 4);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, token);
    }
}
