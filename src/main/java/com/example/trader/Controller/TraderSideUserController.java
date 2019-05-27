package com.example.trader.Controller;

import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.TraderSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/User")
public class TraderSideUserController {
    @Autowired
    private TraderSideUserService traderSideUserService;

    @PostMapping("")
    public ResponseWrapper register(@RequestBody TraderSideUser traderSideUser){
        try {
            TraderSideUser user = traderSideUserService.register(traderSideUser);
            return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, user);
        }
        catch (Exception e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, e.getMessage());
        }
    }

    @GetMapping("/{uid}")
    public ResponseWrapper findById(@PathVariable String userId){
        TraderSideUser user = traderSideUserService.findById(userId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, user);
    }
}
