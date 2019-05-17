package com.example.trader.Dao;

import com.example.trader.Domain.Entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrokerDao extends JpaRepository<Broker, Integer>{

    @Override
    Optional<Broker> findById(Integer integer);

    @Override
    List<Broker> findAll();
}
