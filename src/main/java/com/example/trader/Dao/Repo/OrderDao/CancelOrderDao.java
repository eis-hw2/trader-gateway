package com.example.trader.Dao.Repo.OrderDao;

import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("CancelOrderDao")
@Scope("prototype")
public class CancelOrderDao extends AbstractOrderDao {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getType() {
        return "/" + Order.CANCEL_ORDER;
    }

    public Order modify(String id, Order order) {
        String url = getBroker().getWriteApi() + getType();
        return null;
    }

    public void deleteById(String id) {
        String url = getBroker().getWriteApi() + getType() + "/" + id;
    }

    public Order getById(String  id) {
        String url = getBroker().getReadApi() + getType() + "/" + id;
        return null;
    }
}
