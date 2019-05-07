package com.example.trader.Domain;

public class OrderBlotter {
    public static OrderBlotter createOrderBlotter(Order bigOrder, Order smallOrder, int price){
        return new OrderBlotter();
    }

    String id;
    Order order;


}
