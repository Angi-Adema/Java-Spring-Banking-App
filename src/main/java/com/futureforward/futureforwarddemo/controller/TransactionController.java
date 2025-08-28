package com.futureforward.futureforwarddemo.controller;

import com.futureforward.futureforwarddemo.dto.DepositRequest;
import com.futureforward.futureforwarddemo.dto.TransactionResponse;
import com.futureforward.futureforwarddemo.dto.TransferRequest;
import com.futureforward.futureforwarddemo.entity.Account;
import com.futureforward.futureforwarddemo.entity.Transaction;
import com.futureforward.futureforwarddemo.service.AccountService;
import com.futureforward.futureforwarddemo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    // POST /api/transactions/deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest request) {
        Account account = accountService.getAccountById(request.getAccountId()).orElse(null);
        if (account == null) return ResponseEntity.notFound().build();

        transactionService.deposit(account, request.getAmount());

        // In your current service, deposit returns void; fetch the latest tx list and map last item
        List<Transaction> txs = transactionService.getAllTransactionsForAccount(account.getId());
        Transaction last = txs.get(txs.size() - 1);
        return ResponseEntity.ok(mapToResponse(last));
    }

    // POST /api/transactions/transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        Account from = accountService.getAccountById(request.getFromAccountId()).orElse(null);
        Account to   = accountService.getAccountById(request.getToAccountId()).orElse(null);
        if (from == null || to == null) return ResponseEntity.notFound().build();

        boolean ok = transactionService.transfer(from, to, request.getAmount());
        if (!ok) return ResponseEntity.badRequest().build(); // insufficient funds, etc.

        List<Transaction> txs = transactionService.getAllTransactionsForAccount(from.getId());
        Transaction last = txs.get(txs.size() - 1);
        return ResponseEntity.ok(mapToResponse(last));
    }

    // GET /api/transactions/account/{accountId}
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> listForAccount(@PathVariable Long accountId) {
        List<Transaction> list = transactionService.getAllTransactionsForAccount(accountId);
        if (list == null || list.isEmpty()) return ResponseEntity.ok(List.of());
        return ResponseEntity.ok(list.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    private TransactionResponse mapToResponse(Transaction t) {
        TransactionResponse r = new TransactionResponse();
        r.setId(t.getId());
        r.setType(t.getType().name());
        r.setAmount(t.getAmount());
        r.setTimestamp(t.getTimestamp());
        r.setSourceAccountId(t.getSourceAccount() != null ? t.getSourceAccount().getId() : null);
        r.setDestinationAccountId(t.getDestinationAccount() != null ? t.getDestinationAccount().getId() : null);
        return r;
    }
}
