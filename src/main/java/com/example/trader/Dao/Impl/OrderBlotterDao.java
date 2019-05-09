package com.example.trader.Dao.Impl;


import com.example.trader.Domain.OrderBlotter;
import com.example.trader.Util.DateUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderBlotterDao {

    public List<OrderBlotter> getByFutureIdAndByDate(String futureId, String date){
        return null;
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
