package com.futureforward.futureforwarddemo.controller;

import com.futureforward.futureforwarddemo.dto.DepositRequest;
import com.futureforward.futureforwarddemo.dto.TransactionResponse;
import com.futureforward.futureforwarddemo.dto.TransferRequest;
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
        var accountOpt = accountService.getAccountById(request.getAccountId());
        if (accountOpt.isEmpty()) return ResponseEntity.notFound().build();

        try {
            var tx = transactionService.deposit(accountOpt.get(), request.getAmount());
            return ResponseEntity.ok(mapToResponse(tx));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    // POST /api/transactions/transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        var fromOpt = accountService.getAccountById(request.getFromAccountId());
        var toOpt   = accountService.getAccountById(request.getToAccountId());
        if (fromOpt.isEmpty() || toOpt.isEmpty()) return ResponseEntity.notFound().build();

        try {
            var tx = transactionService.transfer(fromOpt.get(), toOpt.get(), request.getAmount());
            return ResponseEntity.ok(mapToResponse(tx));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    // GET /api/transactions/account/{accountId}
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> listForAccount(@PathVariable Long accountId) {
        var list = transactionService.getAllTransactionsForAccount(accountId);
        var out = list.stream().map(this::mapToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    private TransactionResponse mapToResponse(Transaction t) {
        var r = new TransactionResponse();
        r.setId(t.getId());
        r.setType(t.getType().name());
        r.setAmount(t.getAmount());
        r.setTimestamp(t.getTimestamp());
        r.setSourceAccountId(t.getSourceAccount() != null ? t.getSourceAccount().getId() : null);
        r.setDestinationAccountId(t.getDestinationAccount() != null ? t.getDestinationAccount().getId() : null);
        return r;
    }
}
