package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.entity.Account;
import com.futureforward.futureforwarddemo.entity.Transaction;
import com.futureforward.futureforwarddemo.entity.TransactionType;
import com.futureforward.futureforwarddemo.repository.AccountRepository;
import com.futureforward.futureforwarddemo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public void deposit(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDestinationAccount(account);

        transactionRepository.save(transaction);
    }

    public boolean transfer(Account from, Account to, double amount) {
        if (from.getBalance() < amount) {
            return false; // insufficient funds
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setSourceAccount(from);
        transaction.setDestinationAccount(to);

        transactionRepository.save(transaction);
        return true;
    }

    public List<Transaction> getAllTransactionsForAccount(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId);
    }
}
