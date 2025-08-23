package com.futureforward.futureforwarddemo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DepositRequest {
    @NotNull(message = "accountId is required")
    private Long accountId;

    @Positive(message = "amount must be > 0")
    private double amount;

    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
