package com.example.trader.Dao;

import com.example.trader.Domain.Entity.Future;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FutureDao extends Dao<String ,Future>{
    @Override
    public Future create(Future order) {
        return null;
    }

    @Override
    public Future getById(String id) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Future modify(String id, Future value) {
        return null;
    }
}
