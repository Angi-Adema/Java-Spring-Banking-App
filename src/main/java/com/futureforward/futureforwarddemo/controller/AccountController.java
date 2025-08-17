package com.futureforward.futureforwarddemo.controller;

import com.futureforward.futureforwarddemo.dto.AccountResponse;
import com.futureforward.futureforwarddemo.dto.CreateAccountRequest;
import com.futureforward.futureforwarddemo.entity.Account;
import com.futureforward.futureforwarddemo.entity.Customer;
import com.futureforward.futureforwarddemo.service.AccountService;
import com.futureforward.futureforwarddemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomerService customerService;

    // CREATE account with DTO (Endpoint: POST /api/accounts)
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Optional<Customer> customerOpt = customerService.findById(request.getCustomerId());
        if (customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AccountResponse response = accountService.createAccountFromRequest(request, customerOpt.get());
        return ResponseEntity.ok(response);
    }

    // READ all accounts (Endpoint: GET /api/accounts)
    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts()
                .stream()
                .map(accountService::mapToResponse)
                .collect(Collectors.toList());
    }

    // READ one account (Endpoint: GET /api/accounts/{id})
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(accountService::mapToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE account (Endpoint: PUT /api/accounts/{id})
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@Valid 
                                                         @PathVariable Long id,
                                                         @RequestBody CreateAccountRequest request) {
        Optional<Customer> customerOpt = customerService.findById(request.getCustomerId());
        if (customerOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return accountService.updateAccount(id, request, customerOpt.get())
                .map(accountService::mapToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE account (Endpoint: DELETE /api/accounts/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        boolean deleted = accountService.deleteAccount(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
