package com.example.trader.Controller;

import com.example.trader.Core.MessageQueue.TaskProducer;
import com.example.trader.Core.Processor.ProcessorFactory;
import com.example.trader.Core.Sender.SenderFactory;
import com.example.trader.Dao.Repo.TraderSideDao.OrderToSendDao;
import com.example.trader.Domain.Entity.OrderToSend;
import com.example.trader.Domain.Factory.ResponseWrapperFactory;
import com.example.trader.Domain.Entity.Order;
import com.example.trader.Domain.Wrapper.ResponseWrapper;
import com.example.trader.Exception.InvalidParameterException;
import com.example.trader.Service.OrderService;
import com.example.trader.Util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/Order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderToSendDao orderToSendDao;

    @GetMapping("/future-order")
    public ResponseWrapper getFutureOrder(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderToSend> otss = orderToSendDao.findByTraderSideUsername(username);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, otss);
    }

    @DeleteMapping("/future-order/{groupId}")
    public ResponseWrapper cancelFutureWait(@PathVariable String groupId,
                                            @RequestParam(defaultValue = "2000") Long sleep){
        TaskProducer.cancel(groupId);
        try {
            Thread.sleep(sleep);
        }
        catch (InterruptedException e){}

        List<OrderToSend> otss = orderToSendDao.findByGroupId(groupId);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, otss);
    }

    @PostMapping("")
    public ResponseWrapper createWithStrategy(
            @RequestBody Order order,
            @RequestParam(defaultValue = ProcessorFactory.NONE) String processStrategy,
            @RequestParam(defaultValue = SenderFactory.INSTANT_ONE) String sendStrategy,
            @RequestParam(defaultValue = DateUtil.TOMMOROW_OPEN) String startTime,
            @RequestParam(defaultValue = DateUtil.TOMMOROW_CLOSE) String endTime,
            @RequestParam(defaultValue = "1") Integer slice,
            @RequestParam(defaultValue = "5") Integer intervalMinute,
            @RequestParam Integer brokerId) {

        if (!order.getType().equals("CancelOrder")
                && !order.getType().equals("MarketOrder")
                && !order.getType().equals("LimitOrder"))
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, "Invalid order type:" + order.getType());


        if (!order.getType().equals(Order.CANCEL_ORDER)) {
            if (order.getTotalCount() <= 0)
                return ResponseWrapperFactory.create(ResponseWrapper.ERROR, "Total count must be positive");
            if (order.getFutureName() == null)
                return ResponseWrapperFactory.create(ResponseWrapper.ERROR, "Future name must not be null");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        order.setTraderName(username);

        ProcessorFactory.Parameter pp;
        SenderFactory.Parameter sp;

        try {
            pp = new ProcessorFactory.Parameter(
                    processStrategy,
                    startTime,
                    endTime,
                    slice,
                    brokerId,
                    intervalMinute);
            sp = new SenderFactory.Parameter(
                    sendStrategy,
                    startTime,
                    endTime,
                    brokerId,
                    intervalMinute);

        }
        catch(ParseException e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR,
                    "Time format should be yyyy-mm-dd HH:mm:ss");
        }

        try {
            Object res = orderService.createWithStrategy(username, order, pp, sp);
            return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, res);
        }
        catch (InvalidParameterException e){
            return ResponseWrapperFactory.create(ResponseWrapper.ERROR, e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseWrapper findByBrokerId(@RequestParam Integer brokerId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders = orderService.findByBrokerIdAndUsername(brokerId, username);
        return ResponseWrapperFactory.create(ResponseWrapper.SUCCESS, orders);
    }

}
