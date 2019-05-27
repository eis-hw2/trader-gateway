package com.example.trader.Service.Impl;

import com.example.trader.Dao.Repo.TraderSideUserDao;
import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Service.TraderSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderSideUserServiceImpl implements TraderSideUserService {
    @Autowired
    private TraderSideUserDao traderSideUserDao;

    @Override
    public TraderSideUser register(TraderSideUser traderSideUser) {
        return traderSideUserDao.save(traderSideUser);
    }

    @Override
    public TraderSideUser findById(String id) {
        return traderSideUserDao.findById(id).get();
    }

    @Override
    public TraderSideUser findByUsername(String username) {
        return traderSideUserDao.findByUsername(username);
    }
}
