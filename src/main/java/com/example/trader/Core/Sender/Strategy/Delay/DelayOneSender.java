package com.example.trader.Core.Sender.Strategy.Delay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.trader.Core.MessageQueue.TaskProducer;
import com.example.trader.Core.Sender.Strategy.DelaySender;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Entity.OrderToSend;
import com.example.trader.Domain.Entity.Util.TaskConsumerCommand;
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
    public String send(String traderSideUsername, List<Order> orders) {
        orders.stream().forEach(e -> {
            if (!checkOrder(e))
                throw new RuntimeException("CancelOrder is not allowed in DelaySender.");
        });
        String groupId = UUID.randomUUID().toString();
        int interval = getIntervalMinute();

        Calendar cur = (Calendar) getStartTime().clone();


        orders.stream().forEach(o -> {
            if (o.getTotalCount() == 0)
                return;

            OrderToSend ots = new OrderToSend();
            Integer brokerId = getBrokers().get(0).getId();

            ots.setStatus(OrderToSend.INIT);
            ots.setGroupId(groupId);
            ots.setBrokerId(brokerId);
            ots.setDatetime(DateUtil.calendarToString(cur, DateUtil.datetimeFormat));
            ots.setId(UUID.randomUUID().toString());
            ots.setOrder(o);
            ots.setTraderSideUsername(traderSideUsername);


            TaskProducer.create(ots);

            getOrderToSendDao().save(ots);

            cur.add(Calendar.MINUTE, interval);
        });
        return groupId;
        // set the token when the scheduler is about to send the request
        //AbstractOrderDao orderDao = (AbstractOrderDao)daoFactory.create(getBrokers().get(0), orders.get(0).getType());
        //return orderScheduler.addSplitOrder(traderSideUsername, orders, orderDao, getStartTime(), getEndTime(), getIntervalMinute());
    }


}
