package com.example.trader.Service.Impl;

import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.BrokerSideUser;
import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Service.TraderSideUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BrokerSideUserServiceImpl implements BrokerSideUserService {
    @Autowired
    private BrokerService brokerService;
    @Autowired
    private TraderSideUserService traderSideUserService;

    private static Logger logger = LoggerFactory.getLogger("BrokerSideUserService");

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    public String login(String traderSideUsername, Integer brokerId) {
        TraderSideUser traderSideUser = traderSideUserService.findByUsername(traderSideUsername);
        BrokerSideUser brokerSideUser = traderSideUser.getBrokerSideUsers().get(brokerId);
        Broker broker = brokerService.findById(brokerId);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", brokerSideUser.getTraderName());
        map.add("password", brokerSideUser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String url = broker.getLoginApi() + "/login";
        logger.info("[BrokerSideUserSerivce.login] url: " + url);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        HttpHeaders responseHeaders = response.getHeaders();
        String token = responseHeaders.get("token").get(0);
        logger.info("[BrokerSideUserSerivce.login] token: " + token);
        return token;
    }
}
