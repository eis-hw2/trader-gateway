package com.example.trader.Core.Scheduler;

import com.alibaba.fastjson.JSON;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerSideUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private BrokerSideUserService brokerSideUserService;

    /**
     * Minute
     */
    private final int DEFAULT_INTERVAL = 5;

    private static Logger logger  = LoggerFactory.getLogger("OrderScheduler");

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
    @Bean
    public ConcurrentHashMap<Integer, List<ScheduledFuture>> futures(){
        return new ConcurrentHashMap<>();
    }

    public int addSplitOrder(String username, List<Order> orders, AbstractOrderDao orderDao, Calendar startTime, Calendar endTime){

        Calendar cur = startTime;

        List<ScheduledFuture> futureList = new ArrayList<>();

        orders.stream().forEach( order -> {
            if (order.getCount() == 0)
                return;
            logger.info("[Future.create]: " + cur.getTime() + " " + JSON.toJSONString(order));
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                int hashCode = order.hashCode();
                String token = brokerSideUserService.getToken(username, orderDao.getBroker().getId());
                logger.info("[Future.execute."+hashCode+"]: User: " + username);
                logger.info("[Future.execute."+hashCode+"]: Token: " + token);
                logger.info("[Future.execute."+hashCode+"]: " + cur.getTime() + " " + JSON.toJSONString(order));
                orderDao.setToken(token);
                orderDao.create(order);
            }, cur.getTime());
            futureList.add(future);
            cur.add(Calendar.MINUTE, DEFAULT_INTERVAL);
        });

        int id = orders.hashCode();
        futures.put(id, futureList);
        return id;
    }

    public int addSplitOrder(String username, Map<Order, AbstractOrderDao> orders, Calendar startTime, Calendar endTime){
        Calendar cur = startTime;

        List<ScheduledFuture> futureList = new ArrayList<>();

        orders.entrySet().stream().forEach( entry -> {
            if (entry.getKey().getCount() == 0)
                return;
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                Order o = entry.getKey();
                AbstractOrderDao dao = entry.getValue();

                int hashCode = o.hashCode();
                String token = brokerSideUserService.getToken(username, dao.getBroker().getId());

                logger.info("[Future.execute."+hashCode+"]: User: " + username);
                logger.info("[Future.execute."+hashCode+"]: Token: " + token);
                logger.info("[Future.execute."+hashCode+"]: " + cur.getTime() + " " + JSON.toJSONString(o));

                dao.setToken(token);
                dao.create(o);
            }, cur.getTime());
            futureList.add(future);
            cur.add(Calendar.MINUTE, DEFAULT_INTERVAL);
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
