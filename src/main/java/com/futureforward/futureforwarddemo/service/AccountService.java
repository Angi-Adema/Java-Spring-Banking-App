package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.dto.AccountResponse;
import com.futureforward.futureforwarddemo.dto.CreateAccountRequest;
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

    public AccountResponse createAccountFromRequest(CreateAccountRequest request, Customer customer) {
    Account account = new Account();
    account.setAccountType(request.getAccountType());
    account.setBalance(0.0);
    account.setCustomer(customer);
    Account saved = accountRepository.save(account);
    return mapToResponse(saved);
    }

    public List<Account> getAllAccounts() {
    return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
    return accountRepository.findById(id);
    }

    public Optional<Account> updateAccount(Long id, CreateAccountRequest request, Customer customer) {
    return accountRepository.findById(id).map(existing -> {
        existing.setAccountType(request.getAccountType());
        existing.setCustomer(customer); // only if you want to allow customer switching
        return accountRepository.save(existing);
    });
    }

    public boolean deleteAccount(Long id) {
    if (accountRepository.existsById(id)) {
        accountRepository.deleteById(id);
        return true;
    }
    return false;
    }

    public AccountResponse mapToResponse(Account account) {
    AccountResponse response = new AccountResponse();
    response.setId(account.getId());
    response.setAccountType(account.getAccountType());
    response.setBalance(account.getBalance());
    response.setCustomerId(account.getCustomer().getId());
    return response;
    }
}
