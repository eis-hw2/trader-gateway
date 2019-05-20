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
    private LRUCache<String, DynamicDao> daoCache;

    @Bean
    public LRUCache<String, DynamicDao> daoCache(){
        return new LRUCache<>(5);
    }

    public DynamicDao create(String broker, String type){
        DynamicDao dao = daoCache.get(broker + type);
        if (dao != null) {
            return dao;
        }
        else{
            dao = (DynamicDao)applicationContext.getBean(type + "DynamicDao");
            dao.setSource(broker);
            daoCache.put(broker + type, dao);
            return dao;
        }

    }
}
