package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.entity.Account;
import com.futureforward.futureforwarddemo.entity.Customer;
import com.futureforward.futureforwarddemo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Customer customer, String accountType) {
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public Optional<Account> getAccountByCustomerIdAndType(Long customerId, String accountType) {
        return accountRepository.findByCustomerIdAndAccountType(customerId, accountType);
    }

    public int countAccountsForCustomer(Long customerId) {
        return accountRepository.countByCustomerId(customerId);
    }
}
