package com.example.trader.Core.Processor;

import com.example.trader.Core.Processor.Strategy.MeanProcessor;
import com.example.trader.Core.Processor.Strategy.TwapProcessor;
import com.example.trader.Core.Processor.Strategy.NoneProcessor;
import com.example.trader.Core.Processor.Strategy.VwapProcessor;
import com.example.trader.Dao.Repo.OrderBlotterDao;
import com.example.trader.Exception.UnknownParameterException;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;

@Component
public class ProcessorFactory {
    @Autowired
    OrderBlotterDao orderBlotterDao;

    public final static String VWAP = "VWAP";
    public final static String TWAP = "TWAP";
    public final static String MEAN = "MEAN";
    public final static String NONE = "NONE";

    public static class Parameter{
        private String strategy;
        private Calendar startTime;
        private Calendar endTime;
        private Integer slice;

        public Parameter(String strategy, Calendar startTime, Calendar endTime, Integer slice){
            this.strategy = strategy;
            this.startTime = startTime;
            this.endTime = endTime;
            this.slice = slice;
        }

        public Parameter(String strategy, String startTime, String endTime, Integer slice) throws ParseException{
            this.startTime = DateUtil.stringToCalendar(startTime, DateUtil.datetimeFormat);
            this.endTime = DateUtil.stringToCalendar(endTime, DateUtil.datetimeFormat);
            this.strategy = strategy;
            this.slice = slice;
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

        public Integer getSlice() {
            return slice;
        }

        public void setSlice(Integer slice) {
            this.slice = slice;
        }
    }

    public Processor create(Parameter parameter){
        Processor p;
        switch (parameter.getStrategy()){
            case VWAP:
                p = new VwapProcessor(parameter.getStartTime(), parameter.getEndTime(), orderBlotterDao);
                break;
            case TWAP:
                p = new TwapProcessor(parameter.getStartTime(), parameter.getEndTime());
                break;
            case MEAN:
                p = new MeanProcessor(parameter.getSlice());
                break;
            case NONE:
                p = new NoneProcessor();
                break;
            default:
                throw new UnknownParameterException("Unknown Processor Strategy: " + parameter.getStrategy());
        }
        return p;
    }
}
