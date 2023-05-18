package com.exercise.bankaccounts.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountRequest {
    private BigDecimal allowedMinus = BigDecimal.valueOf(0);

    private BigDecimal limitWithdraw = BigDecimal.valueOf(2000);
}
