package com.example.trader.Controller;

import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.BrokerSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/v1/BrokerSideUser")
public class BrokerSideUserController {
    @Autowired
    private BrokerSideUserService brokerSideUserService;

    @GetMapping("/testConnection/{brokerId}")
    public ResponseWrapper testConnection(@PathVariable Integer brokerId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            String token = brokerSideUserService.login(username, brokerId);
            return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, "Login Success");
        }
        catch (HttpClientErrorException e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, "Login Failure");
        }
    }
}
