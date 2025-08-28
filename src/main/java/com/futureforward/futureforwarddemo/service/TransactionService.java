package com.futureforward.futureforwarddemo.service;

import com.futureforward.futureforwarddemo.entity.Account;
import com.futureforward.futureforwarddemo.entity.Transaction;
import com.futureforward.futureforwarddemo.entity.TransactionType;
import com.futureforward.futureforwarddemo.repository.AccountRepository;
import com.futureforward.futureforwarddemo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.DEPOSIT);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setDestinationAccount(account);

        return transactionRepository.save(tx);
    }

    @Transactional
    public Transaction transfer(Account from, Account to, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        if (from.getId().equals(to.getId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }

        if (from.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        accountRepository.save(from);
        accountRepository.save(to);

        Transaction tx = new Transaction();
        tx.setType(TransactionType.TRANSFER);
        tx.setAmount(amount);
        tx.setTimestamp(LocalDateTime.now());
        tx.setSourceAccount(from);
        tx.setDestinationAccount(to);

        return transactionRepository.save(tx);
    }

    public List<Transaction> getAllTransactionsForAccount(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId);
    }
}
