package com.example.trader.Service;

import com.example.trader.Domain.Entity.TraderSideUser;

public interface TraderSideUserService {
    TraderSideUser register(TraderSideUser traderSideUser);

    TraderSideUser findById(String id);

    TraderSideUser findByUsername(String username);
}
