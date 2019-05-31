package com.example.trader.Dao.Repo.OrderDao;

import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("StopOrderDao")
@Scope("prototype")
public class StopOrderDao extends AbstractOrderDao {
    private static Logger logger = LoggerFactory.getLogger("StopOrderDao");

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getType() {
        return "stopOrders";
    }

}
