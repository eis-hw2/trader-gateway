package com.example.trader.Dao.Repo.OrderDao;

import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("LimitOrderDao")
@Scope("prototype")
public class LimitOrderDao extends AbstractOrderDao {

    @Override
    public String getType() {
        return "limitOrders";
    }


}
