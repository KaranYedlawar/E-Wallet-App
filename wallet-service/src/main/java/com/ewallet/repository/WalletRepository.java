package com.ewallet.repository;

import com.ewallet.entity.Wallet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByUserId(String userId);

    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = w.balance + :amount where w.id = :id")
    void updateWallet(Long id, Long amount);
}
