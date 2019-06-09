package com.example.trader.Core.Sender;

import com.example.trader.Core.Sender.Strategy.Instant.InstantDistributeSender;
import com.example.trader.Core.Sender.Strategy.Instant.InstantOneSender;
import com.example.trader.Core.Sender.Strategy.Delay.DelayDistributeSender;
import com.example.trader.Core.Sender.Strategy.Delay.DelayOneSender;
import com.example.trader.Dao.Factory.DaoFactory;
import com.example.trader.Dao.Repo.TraderSideDao.OrderToSendDao;
import com.example.trader.Domain.Entity.Broker;
import com.example.trader.Exception.InvalidParameterException;
import com.example.trader.Service.BrokerService;
import com.example.trader.Service.BrokerSideUserService;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class SenderFactory {

    @Autowired
    private BrokerService brokerService;
    @Autowired
    private DaoFactory daoFactory;
    @Autowired
    private BrokerSideUserService brokerSideUserService;
    @Autowired
    private OrderToSendDao orderToSendDao;

    public final static String DELAY_DISTRIBUTE = "DELAY_DISTRIBUTE";
    public final static String DELAY_ONE = "DELAY_ONE";
    public final static String INSTANT_DISTRIBUTE = "INSTANT_DISTRIBUTE";
    public final static String INSTANT_ONE = "INSTANT_ONE";

    private List<Broker> getBroker(String strategy, Integer brokerId){

        switch (strategy){
            case DELAY_ONE:
            case INSTANT_ONE:
                List<Broker> brokers = new ArrayList<>();
                brokers.add(brokerService.findById(brokerId));
                return brokers;
            case INSTANT_DISTRIBUTE:
            case DELAY_DISTRIBUTE:
                return brokerService.findAll();
            default:
                return null;
        }
    }

    public Sender create(SenderFactory.Parameter parameter){
        List<Broker> brokers = getBroker(parameter.getStrategy(), parameter.getBrokerId());
        Sender res;
        switch (parameter.getStrategy()){
            case DELAY_DISTRIBUTE:
                DelayDistributeSender s1 = new DelayDistributeSender(parameter.getIntervalMinute());
                s1.setStartTime(parameter.getStartTime());
                s1.setEndTime(parameter.getEndTime());
                s1.setOrderToSendDao(orderToSendDao);
                s1.setBrokers(brokers);
                res = s1;
                break;

            case DELAY_ONE:
                DelayOneSender s2 = new DelayOneSender(parameter.getIntervalMinute());
                s2.setStartTime(parameter.getStartTime());
                s2.setEndTime(parameter.getEndTime());
                s2.setOrderToSendDao(orderToSendDao);
                s2.setBrokers(brokers);
                res = s2;
                break;

            case INSTANT_DISTRIBUTE:
                InstantDistributeSender s3 = new InstantDistributeSender(daoFactory, brokerSideUserService);
                s3.setBrokers(brokers);
                res = s3;
                break;
            case INSTANT_ONE:
                InstantOneSender s4 = new InstantOneSender(daoFactory, brokerSideUserService);
                s4.setBrokers(brokers);
                res = s4;
                break;

            default:
                throw new InvalidParameterException("Unknown Sender Strategy: " + parameter.getStrategy());
        }

        return res;
    }

    public static class Parameter{
        private String strategy;
        private Calendar startTime;
        private Calendar endTime;
        private Integer brokerId;
        private Integer intervalMinute;

        public Parameter(String strategy, String startTime, String endTime, Integer brokerId, Integer intervalMinute) throws ParseException {

            this.startTime = DateUtil.stringToCalendar(startTime, DateUtil.datetimeFormat);
            this.endTime = DateUtil.stringToCalendar(endTime, DateUtil.datetimeFormat);
            this.strategy = strategy;
            this.brokerId = brokerId;
            this.intervalMinute = intervalMinute;
        }

        public Parameter(String strategy, Integer brokerId){
            this.strategy = strategy;
            this.brokerId = brokerId;
        }

        public Parameter(){}

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public Calendar getStartTime() {
            return startTime;
        }

        public void setStartTime(Calendar startTime) {
            this.startTime = startTime;
        }

        public Calendar getEndTime() {
            return endTime;
        }

        public void setEndTime(Calendar endTime) {
            this.endTime = endTime;
        }

        public Integer getBrokerId() {
            return brokerId;
        }

        public void setBrokerId(Integer brokerId) {
            this.brokerId = brokerId;
        }

        public Integer getIntervalMinute() {
            return intervalMinute;
        }

        public void setIntervalMinute(Integer intervalMinute) {
            this.intervalMinute = intervalMinute;
        }
    }
}
