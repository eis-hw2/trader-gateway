package com.example.trader.Domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class OrderBook {
    class Composite{
        int price;
        int count;

        public Composite(){}

        public Composite(int count, int price) {
            this.price = price;
            this.count = count;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
    String id;
    List<Composite> buyers = new ArrayList<>();
    List<Composite> sellers = new ArrayList<>();

    public OrderBook() {
    }

    public List<Composite> getBuyers() {
        return buyers;
    }

    public void setBuyers(List<Composite> buyers) {
        this.buyers = buyers;
    }

    public List<Composite> getSellers() {
        return sellers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSellers(List<Composite> sellers) {
        this.sellers = sellers;
    }
}