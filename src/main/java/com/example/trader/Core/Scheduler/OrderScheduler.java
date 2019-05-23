package com.example.trader.Core.Scheduler;

import com.alibaba.fastjson.JSON;
import com.example.trader.Dao.Repo.DynamicDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class OrderScheduler {
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Autowired
    private ConcurrentHashMap<Integer, List<ScheduledFuture>> futures;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
    @Bean
    public ConcurrentHashMap<Integer, List<ScheduledFuture>> futures(){
        return new ConcurrentHashMap<>();
    }

    // TODO
    public int addSplitOrder(List<Order> orders, DynamicDao orderDao){
        Calendar calendar = Calendar.getInstance();
        // after 5 min
        //calendar.add(Calendar.MINUTE, 5);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);


        List<ScheduledFuture> futureList = new ArrayList<>();

        orders.stream().forEach( order -> {
            if (order.getCount() == 0)
                return;
            System.out.println("[Future.create]: " + calendar.getTime() + " " + JSON.toJSONString(order));
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                System.out.println("[Future.execute]: " + calendar.getTime() + " " + JSON.toJSONString(order));
                orderDao.create(order);
            }, calendar.getTime());
            futureList.add(future);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        });

        int id = orders.hashCode();
        futures.put(id, futureList);
        return id;
    }

    public int addSplitOrder(Map<Order, DynamicDao> orders){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        List<ScheduledFuture> futureList = new ArrayList<>();

        orders.entrySet().stream().forEach( entry -> {
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                entry.getValue().create(entry.getKey());
            }, calendar.getTime());
            futureList.add(future);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        });

        int id = futureList.hashCode();
        futures.put(id, futureList);
        return id;
    }


    public int add(List<Runnable> task, Trigger trigger){
        return 0;
    }

    public boolean cancel(int id){
        List<ScheduledFuture> futureList = futures.get(id);
        futureList.stream().forEach(e -> {
            if (!e.isDone())
                e.cancel(true);
        });
        return true;
    }
}
