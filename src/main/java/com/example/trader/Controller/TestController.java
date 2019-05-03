package com.example.trader.Controller;

import com.example.trader.Service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    WebSocketService webSocketService;
}
