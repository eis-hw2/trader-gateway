package com.example.trader.Domain.Entity.Util;

import java.util.ArrayList;
import java.util.List;

public class Role {
    public final static String ROLE_TRADER = "ROLE_TRADER";
    public final static String TRADER = "TRADER";

    public final static List<String> getDefaultRole(){
        List<String> res = new ArrayList<>();
        res.add(ROLE_TRADER);
        return res;
    }
}
