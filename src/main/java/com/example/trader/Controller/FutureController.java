package com.example.trader.Controller;

import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Future")
public class FutureController {
    @Autowired
    private FutureService futureService;

    @GetMapping("")
    public ResponseWrapper findAll(@RequestParam Integer brokerId){
        List<Future> futures = futureService.findAll(brokerId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, futures);
    }

    @GetMapping("/{futureId}")
    public ResponseWrapper findById(@RequestParam Integer brokerId, @PathVariable String futureId){
        Future future = futureService.findById(brokerId, futureId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, future);
    }
}
