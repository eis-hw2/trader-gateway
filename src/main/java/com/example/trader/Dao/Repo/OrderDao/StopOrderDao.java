package com.example.trader.Dao.Repo.OrderDao;

import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("StopOrderDao")
@Scope("prototype")
public class StopOrderDao extends AbstractOrderDao {

    private RestTemplate restTemplate;

    @Override
    public String getType() {
        return "/" + Order.STOP_ORDER;
    }

}
