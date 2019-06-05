package com.example.trader.Core.Sender.Strategy.Delay;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.MessageQueue.TaskProducer;
import com.example.trader.Core.Scheduler.OrderScheduler;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Dao.Repo.AbstractOrderDao;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.OrderToSend;
import com.example.trader.Util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
* Send the Order to Different Brokers
*/
public class DelayDistributeSender extends DelaySender {

    private final static Logger logger = LoggerFactory.getLogger("DelayDistributeSender");

    public DelayDistributeSender(int intervalMinute){
        this.setIntervalMinute(intervalMinute);
    }

    @Override
    public Integer send(String traderSideUsername, List<Order> orders) {

        int interval = getIntervalMinute();

        Calendar cur = (Calendar) getStartTime().clone();

        int size = getBrokers().size();
        int curIndex = 0;

        for (Order order: orders){
            if (order.getTotalCount() == 0)
                continue;
            OrderToSend ots = new OrderToSend();
            Integer brokerId = getBrokers().get(curIndex % size).getId();
            ots.setBrokerId(brokerId);
            ots.setDatetime(DateUtil.calendarToString(cur, DateUtil.datetimeFormat));
            ots.setId(UUID.randomUUID().toString());
            ots.setOrder(order);
            ots.setTraderSideUsername(traderSideUsername);
            String message = JSON.toJSONString(ots);

            logger.info("[DelayOneSender.send] OrderToSend: " + message);
            TaskProducer.produce(message);

            cur.add(Calendar.MINUTE, interval);
            ++curIndex;
        }
        return 0;
    }
}
