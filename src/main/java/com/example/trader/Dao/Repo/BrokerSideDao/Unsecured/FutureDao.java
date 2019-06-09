package com.example.trader.Dao.Repo.BrokerSideDao.Unsecured;

import com.example.trader.Dao.Repo.BrokerSideDao.DynamicDao;
import com.example.trader.Dao.Repo.BrokerSideDao.UnsecuredDao;
import com.example.trader.Domain.Entity.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("FutureDao")
@Scope("prototype")
public class FutureDao extends UnsecuredDao<String ,Future> {

    private static Logger logger = LoggerFactory.getLogger("FutureDao");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getType() {
        return "futures";
    }

    @Override
    public Class<Future> getValueClass() {
        return Future.class;
    }

    @Override
    public Class<Future[]> getValueArrayClass() {
        return Future[].class;
    }

}
