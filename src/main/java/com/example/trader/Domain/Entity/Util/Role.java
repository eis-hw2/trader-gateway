package com.example.trader.Domain.Entity.Util;

import java.util.ArrayList;
import java.util.List;

public class Role {
    public final static String TRADER = "TRADER";

    public final static List<String> getDefaultRole(){
        List<String> res = new ArrayList<>();
        res.add(TRADER);
        return res;
    }
}
