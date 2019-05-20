package com.example.trader.Controller;

import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Future")
public class FutureController {
    @Autowired
    private FutureService futureService;

    @GetMapping("")
    public ResponseWrapper getAll(@RequestParam Integer brokerId){
        List<Future> futures = futureService.getAll(brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, futures);
    }
}
