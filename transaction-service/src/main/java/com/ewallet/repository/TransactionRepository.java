package com.ewallet.repository;

import com.ewallet.entity.Transaction;
import com.ewallet.entity.TransactionStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Modifying
    @Transactional
    @Query("update transaction t set t.status = ?2 where t.externalTransactionId = ?1")
    void updateTransaction(String externalTransactionId, TransactionStatus status);
}
