package com.example.trader.Service.Impl;

import com.example.trader.Dao.Repo.TraderSideUserDao;
import com.example.trader.Domain.Entity.BrokerSideUser;
import com.example.trader.Domain.Entity.TraderSideUser;
import com.example.trader.Domain.Entity.Util.Role;
import com.example.trader.Service.TraderSideUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TraderSideUserServiceImpl implements TraderSideUserService {
    @Autowired
    private TraderSideUserDao traderSideUserDao;

    @Override
    public TraderSideUser register(TraderSideUser traderSideUser) {
        traderSideUser.setBrokerSideUsers(new HashMap<>());
        traderSideUser.setRoles(Role.getDefaultRole());
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

    @Override
    public List<TraderSideUser> findAll() {
        return traderSideUserDao.findAll();
    }

    @Secured(Role.TRADER)
    @Override
    public BrokerSideUser addBrokerSideUser(String username, BrokerSideUser brokerSideUser) {
        TraderSideUser traderSideUser = traderSideUserDao.findByUsername(username);
        BrokerSideUser res = traderSideUser.addBrokerSideUser(brokerSideUser);
        traderSideUserDao.save(traderSideUser);
        return res;
    }

    @Secured(Role.TRADER)
    @Override
    public BrokerSideUser removeBrokerSideUser(String username, Integer bid) {
        TraderSideUser traderSideUser = traderSideUserDao.findByUsername(username);
        BrokerSideUser res = traderSideUser.removeBrokerSideUser(bid);
        traderSideUserDao.save(traderSideUser);
        return res;
    }

    @Secured(Role.TRADER)
    @Override
    public BrokerSideUser modifyBrokerSideUser(String username, BrokerSideUser brokerSideUser) {
        TraderSideUser traderSideUser = traderSideUserDao.findByUsername(username);
        BrokerSideUser res = traderSideUser.modifyBrokerSideUser(brokerSideUser);
        traderSideUserDao.save(traderSideUser);
        return res;
    }
}
