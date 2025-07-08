package com.futureforward.furtureforwarddemo.repository;

import com.futureforward.futureforwarddemo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    List<Account> findByCustomerId(Long customerId);

    Optional<Account> findByCustomerIdAndAccountType(Long customerId, String accountType);

    int countByCustomerId(Long customerId);
}
