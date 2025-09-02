package com.futureforward.futureforwarddemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DepositRequest {
    @NotNull(message = "accountId is required")
    private Long accountId;

    @Positive(message = "amount must be > 0")
    private double amount;

    // Getters/Setters set up here so other parts of the program such as services, controllers or serialization libraries such as Jackson
    // can read the private set fields in a safe and controlled way. These fields are marked private so they cannot be accessed directly outside the class.
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
