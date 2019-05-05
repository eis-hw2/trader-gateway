package com.example.trader.Entity;

import java.util.ArrayList;
import java.util.List;

public class OrderBook {
    class Pair{
        int count;
        int unitPrice;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public Pair(int count, int unitPrice) {
            this.count = count;
            this.unitPrice = unitPrice;
        }
    }
    List<Pair> buyer = new ArrayList<>();
    List<Pair> seller = new ArrayList<>();

    public OrderBook() {
    }

    public List<Pair> getBuyer() {
        return buyer;
    }

    public void setBuyer(List<Pair> buyer) {
        this.buyer = buyer;
    }

    public List<Pair> getSeller() {
        return seller;
    }

    public void setSeller(List<Pair> seller) {
        this.seller = seller;
    }
}