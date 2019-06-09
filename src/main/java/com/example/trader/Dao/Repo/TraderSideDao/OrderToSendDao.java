package com.example.trader.Dao.Repo.TraderSideDao;

import com.example.trader.Domain.Entity.OrderToSend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderToSendDao extends MongoRepository<OrderToSend, String> {
    List<OrderToSend> findByGroupId(String groupId);
    List<OrderToSend> findByTraderSideUsername(String username);
}
