package com.example.trader.Dao.Repo;

import com.example.trader.Domain.Entity.OrderBlotter;
import com.example.trader.Util.DateUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("OrderBlotterDao")
@Scope("prototype")
public class OrderBlotterDao extends DynamicDao<String ,OrderBlotter>{

    @Override
    public String getType() {
        return "orderBlotters";
    }

    @Override
    public Class<OrderBlotter> getValueClass() {
        return OrderBlotter.class;
    }

    @Override
    public Class<OrderBlotter[]> getValueArrayClass() {
        return OrderBlotter[].class;
    }

    public List<OrderBlotter> findByFutureIdAndDate(String futureId, String date){
        // todo
        return null;
    }

    public List<OrderBlotter> findByFutureIdAndTime(String futureId, Calendar startTime, Calendar endTime){
        // todo
        return null;
    }

    public List<OrderBlotter> findByFutureIdYesterday(String futureId){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String yesterday = DateUtil.format.format(calendar.getTime());
        return findByFutureIdAndDate(futureId, yesterday);
    }

    public static void main(String[] args){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        System.out.print(DateUtil.format.format(calendar.getTime()));
    }
}
