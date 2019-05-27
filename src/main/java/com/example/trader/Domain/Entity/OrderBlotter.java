package com.example.trader.Domain.Entity;


import com.example.trader.Domain.Entity.Util.Links;

public class OrderBlotter {

    private String id;
    private int count;
    private int price;
    private Links _links;

    public Links get__links() {
        return _links;
    }

    public void set__links(Links _links) {
        this._links = _links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
