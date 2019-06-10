package com.example.trader.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.BrokerSideUser;
import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Domain.Entity.Util.Role;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Service.RedisService;
import com.example.trader.Service.TraderSideUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class BrokerSideUserServiceImpl implements BrokerSideUserService {
    private static final Long DEFAULT_EXPIRATION = Duration.ofDays(365).getSeconds();

    @Autowired
    private BrokerService brokerService;
    @Autowired
    private TraderSideUserService traderSideUserService;

    private static Logger logger = LoggerFactory.getLogger("BrokerSideUserService");
    private static final String REDIS_KEY_PREFIX = "trader-gateway:BrokerSideUserService:login:";

    @Autowired
    private RedisService redisService;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Secured(Role.TRADER)
    @Override
    public String getToken(String traderSideUsername, Integer brokerId){
        String key = getKey(traderSideUsername, brokerId);
        if (redisService.exists(key))
            return redisService.get(key, String.class);
        return login(traderSideUsername, brokerId);
    }

    @Secured(Role.TRADER)
    @Override
    public String login(String traderSideUsername, Integer brokerId) {
        logger.info("[BrokerSideUserService.login] TraderSideUsername: " + traderSideUsername);
        logger.info("[BrokerSideUserService.login] BrokerId: " + brokerId);
        TraderSideUser traderSideUser = traderSideUserService.findByUsername(traderSideUsername);
        BrokerSideUser brokerSideUser = traderSideUser.getBrokerSideUser(brokerId);
        //logger.info("[BrokerSideUserService.login] BrokerSideUser: " + JSON.toJSONString(brokerSideUser));
        Broker broker = brokerService.findById(brokerId);

        String url = broker.getLoginApi() + "/login";
        logger.info("[BrokerSideUserSerivce.login] url: " + url);

        ResponseEntity<String> response = _login(brokerSideUser.getUsername(),
                brokerSideUser.getPassword(), url);

        HttpHeaders responseHeaders = response.getHeaders();
        String token = responseHeaders.get("token").get(0);
        logger.info("[BrokerSideUserSerivce.login] token: " + token);

        String key =  getKey(traderSideUsername, brokerId);
        redisService.set(key, token, DEFAULT_EXPIRATION);
        return token;
    }

    @Override
    public boolean login(BrokerSideUser brokerSideUser) {
        try {
            logger.info("[BrokerSideUserService.login] BrokerSideUser: " + JSON.toJSONString(brokerSideUser));
            //logger.info("[BrokerSideUserService.login] BrokerSideUser: " + JSON.toJSONString(brokerSideUser));
            Broker broker = brokerService.findById(brokerSideUser.getBrokerId());
            String url = broker.getLoginApi() + "/login";

            ResponseEntity<String> response = _login(brokerSideUser.getUsername(),
                    brokerSideUser.getPassword(), url);

            HttpHeaders responseHeaders = response.getHeaders();
            String token = responseHeaders.get("token").get(0);
            logger.info("[BrokerSideUserSerivce.login] token: " + token);
            if (token == null)
                return false;
            return true;
        }
        catch(Exception e){
            logger.error("[BrokerSideUserService.login] Error :" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private ResponseEntity<String> _login(String username, String password, String loginApi){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        logger.info("[BrokerSideUserSerivce.login] url: " + loginApi);

        ResponseEntity<String> response = restTemplate.postForEntity(loginApi, request, String.class);
        return response;
    }

    private String getKey(String username, Integer brokerId){
        return REDIS_KEY_PREFIX + username + ":" + brokerId;
    }
}
