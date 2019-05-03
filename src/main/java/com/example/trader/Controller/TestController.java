package com.example.trader.Controller;

import com.example.trader.Service.WebSocketService;
import com.example.trader.Util.ResponseWrapperFactory;
import com.example.trader.Util.Wrapper.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/test")
public class TestController {
    @Autowired
    WebSocketService webSocketService;

    @PostMapping("/broadcast/{message}")
    ResponseWrapper broadcast(@PathVariable String message){
        webSocketService.broadcast(message);
        return ResponseWrapperFactory.create("success", "Message: " + message);
    }
}
