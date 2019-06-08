package com.example.trader.Core.Sender.Strategy.Delay;

import com.alibaba.fastjson.JSON;
import com.example.trader.Core.MessageQueue.TaskProducer;
import com.example.trader.Core.Sender.Strategy.DelaySender;
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
    public String send(String traderSideUsername, List<Order> orders) {
        orders.stream().forEach(e -> {
            if (!checkOrder(e))
                throw new RuntimeException("CancelOrder is not allowed in DelaySender.");
        });
        String groupId = UUID.randomUUID().toString();
        int interval = getIntervalMinute();

        Calendar cur = (Calendar) getStartTime().clone();

        int size = getBrokers().size();
        int curIndex = 0;

        for (Order order: orders){
            if (order.getTotalCount() == 0)
                continue;
            OrderToSend ots = new OrderToSend();
            Integer brokerId = getBrokers().get(curIndex % size).getId();

            ots.setStatus(OrderToSend.INIT);
            ots.setGroupId(groupId);
            ots.setBrokerId(brokerId);
            ots.setDatetime(DateUtil.calendarToString(cur, DateUtil.datetimeFormat));
            ots.setId(UUID.randomUUID().toString());
            ots.setOrder(order);
            ots.setTraderSideUsername(traderSideUsername);

            TaskProducer.create(ots);

            getOrderToSendDao().save(ots);

            cur.add(Calendar.MINUTE, interval);
            ++curIndex;
        }
        return groupId;
    }
}
