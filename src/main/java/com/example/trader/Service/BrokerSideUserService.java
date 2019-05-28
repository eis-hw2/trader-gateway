package com.example.trader.Service;

import com.example.trader.Domain.Entity.BrokerSideUser;
import com.example.trader.Domain.Entity.TraderSideUser;

public interface BrokerSideUserService {
    String login(String traderSideUsername, Integer brokerId);

    String getToken(String traderSideUsername, Integer brokerId);
}
