package com.example.trader.Dao.Repo.BrokerSideDao;

public abstract class SecuredDao<K, V> extends DynamicDao<K, V>{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
