package com.example.trader.Dao.Repo.BrokerSideDao;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class SecuredDao<K, V> extends DynamicDao<K, V>{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token", getToken());
        getLogger().info("[SecuredDao.getHttpHeaders] token:" + getToken());
        return headers;
    }
}
