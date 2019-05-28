package com.example.trader.Dao.Repo;

import com.example.trader.Domain.Entity.TraderSideUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TraderSideUserDao extends MongoRepository<TraderSideUser, String> {
    TraderSideUser findByUsername(String username);
}
