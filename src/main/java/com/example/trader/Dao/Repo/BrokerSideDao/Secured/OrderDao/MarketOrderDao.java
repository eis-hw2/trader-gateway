package com.example.trader.Dao.Repo.BrokerSideDao.Secured.OrderDao;

import com.example.trader.Dao.Repo.BrokerSideDao.Secured.AbstractOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("MarketOrderDao")
@Scope("prototype")
public class MarketOrderDao extends AbstractOrderDao {

    private static Logger logger = LoggerFactory.getLogger("MarketOrderDao");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getType() {
        return "marketOrders";
    }

}
