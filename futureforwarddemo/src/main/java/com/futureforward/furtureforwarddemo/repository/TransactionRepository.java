package com.futureforward.furtureforwarddemo.repository;

import com.futureforward.futureforwarddemo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findBySourceAccountIdOrDestinationAccountId(Long sourceId, Long destinationId);
}
