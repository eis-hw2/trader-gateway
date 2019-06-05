package com.example.trader.Core.Sender.Strategy.Delay;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.MessageQueue.TaskProducer;
import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.OrderToSend;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
* Send the Order to One Broker
*/
public class DelayOneSender extends DelaySender {

    private final static Logger logger = LoggerFactory.getLogger("DelayOneSender");

    public DelayOneSender(int intervalMinute){
        this.setIntervalMinute(intervalMinute);
    }

    @Override
    public Integer send(String traderSideUsername, List<Order> orders) {
        int interval = getIntervalMinute();

        Calendar cur = (Calendar) getStartTime().clone();


        orders.stream().forEach(o -> {
            if (o.getTotalCount() == 0)
                return;

            OrderToSend ots = new OrderToSend();
            Integer brokerId = getBrokers().get(0).getId();
            ots.setBrokerId(brokerId);
            ots.setDatetime(DateUtil.calendarToString(cur, DateUtil.datetimeFormat));
            ots.setId(UUID.randomUUID().toString());
            ots.setOrder(o);
            ots.setTraderSideUsername(traderSideUsername);
            String message = JSON.toJSONString(ots);

            logger.info("[DelayOneSender.send] OrderToSend: " + message);
            TaskProducer.produce(message);

            cur.add(Calendar.MINUTE, interval);
        });
        return 0;
        // set the token when the scheduler is about to send the request
        //AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(getBrokers().get(0), orders.get(0).getType());
        //return orderScheduler.addSplitOrder(traderSideUsername, orders, orderDao, getStartTime(), getEndTime(), getIntervalMinute());
    }


}
