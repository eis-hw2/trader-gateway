package com.example.trader.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DaoFactory {
    @Autowired
    private ApplicationContext applicationContext;

    private Map<String, Dao> daos = new ConcurrentHashMap<>();

    public <T> T create(String broker, Class<T> type){

        String key = broker + type.getName();
        if (daos.containsKey(key)){
            return (T)daos.get(key);
        }
        Dao dao = (Dao)(applicationContext.getBean(type));
        dao.setSource(broker);
        daos.put(key, dao);
        return (T)(dao);
    }
}
