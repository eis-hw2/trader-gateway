package com.example.trader.Dao.Repo;

import com.example.trader.Domain.Entity.Future;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("FutureDao")
@Scope("prototype")
public class FutureDao extends DynamicDao<String ,Future> {
    @Override
    public Future create(Future order) {
        return null;
    }

}
