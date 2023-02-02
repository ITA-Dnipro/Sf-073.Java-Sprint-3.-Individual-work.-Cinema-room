package com.example.antifraud.repository;


import com.example.antifraud.model.entities.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StolenCreditCard extends JpaRepository<CreditCardEntity,Long> {
    boolean existsByNumber(String number);
    int deleteByNumber(String number);
}
