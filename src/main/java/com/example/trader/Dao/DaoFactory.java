package com.example.trader.Dao;

import com.example.trader.Util.LRUCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LRUCache<String, Dao> daoCache;

    @Bean
    public LRUCache<String, Dao> daoCache(){
        return new LRUCache<>(5);
    }

    public Dao create(String broker, String type){
        Dao dao = daoCache.get(broker + type);
        if (dao != null) {
            return dao;
        }
        else{
            dao = (Dao)applicationContext.getBean(type + "Dao");
            dao.setSource(broker);
            daoCache.put(broker + type, dao);
            return dao;
        }

    }
}
