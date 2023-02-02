package com.example.antifraud.repository;


import com.example.antifraud.model.entities.IpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IpRepository extends JpaRepository<IpEntity,Long> {
    boolean existsByIp(String ip);
    int deleteByIp(String ip);
}
