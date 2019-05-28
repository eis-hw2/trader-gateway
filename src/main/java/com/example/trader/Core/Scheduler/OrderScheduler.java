package com.example.trader.Core.Scheduler;

import com.alibaba.fastjson.JSON;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Repo.DynamicDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Util.DateUtil;
import net.bytebuddy.asm.Advice;
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

    private static Logger logger  = LoggerFactory.getLogger("OrderScheduler");

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }
    @Bean
    public ConcurrentHashMap<Integer, List<ScheduledFuture>> futures(){
        return new ConcurrentHashMap<>();
    }

    // TODO
    public int addSplitOrder(String username, List<Order> orders, AbstractOrderDao orderDao){
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
            logger.info("[Future.create]: " + calendar.getTime() + " " + JSON.toJSONString(order));
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                int hashCode = order.hashCode();
                String token = brokerSideUserService.getToken(username, orderDao.getBroker().getId());
                logger.info("[Future.execute."+hashCode+"]: User: " + username);
                logger.info("[Future.execute."+hashCode+"]: Token: " + token);
                logger.info("[Future.execute."+hashCode+"]: " + calendar.getTime() + " " + JSON.toJSONString(order));
                orderDao.setToken(token);
                orderDao.create(order);
            }, calendar.getTime());
            futureList.add(future);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        });

        int id = orders.hashCode();
        futures.put(id, futureList);
        return id;
    }

    public int addSplitOrder(String username, Map<Order, AbstractOrderDao> orders){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        List<ScheduledFuture> futureList = new ArrayList<>();

        orders.entrySet().stream().forEach( entry -> {
            ScheduledFuture future = threadPoolTaskScheduler.schedule(() -> {
                Order o = entry.getKey();
                AbstractOrderDao dao = entry.getValue();

                int hashCode = o.hashCode();
                String token = brokerSideUserService.getToken(username, dao.getBroker().getId());

                logger.info("[Future.execute."+hashCode+"]: User: " + username);
                logger.info("[Future.execute."+hashCode+"]: Token: " + token);
                logger.info("[Future.execute."+hashCode+"]: " + calendar.getTime() + " " + JSON.toJSONString(o));

                dao.setToken(token);
                dao.create(o);
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
