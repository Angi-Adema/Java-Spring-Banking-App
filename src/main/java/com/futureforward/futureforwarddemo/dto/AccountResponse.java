package com.futureforward.futureforwarddemo.dto;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String accountType;
    private double balance;
    private Long customerId;
}
