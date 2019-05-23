package com.example.trader.Dao.Repo;


import com.alibaba.fastjson.JSON;
import com.example.trader.Domain.Entity.Future;
import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Util.DateUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;

@Component("OrderBlotterDao")
@Scope("prototype")
public class OrderBlotterDao extends DynamicDao<String ,OrderBlotter>{

    @Override
    public String getType() {
        return "/OrderBlotter";
    }

    //todo
    public List<OrderBlotter> getByFutureIdAndByDate(String futureId, String date){
        String url = getBroker().getReadApi() + getType();
        ResponseEntity<OrderBlotter[]> responseEntity = getRestTemplate().getForEntity(url, OrderBlotter[].class);
        List<OrderBlotter> res = Arrays.asList(responseEntity.getBody());
        System.out.println(JSON.toJSONString(res));
        return res;
    }

    public List<OrderBlotter> getByFutureIdYesterday(String futureId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = DateUtil.format.format(calendar.getTime());
        return getByFutureIdAndByDate(futureId, yesterday);
    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        System.out.print(DateUtil.format.format(calendar.getTime()));
    }
}
