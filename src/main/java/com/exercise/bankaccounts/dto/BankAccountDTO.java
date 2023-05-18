package com.exercise.bankaccounts.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDTO {
    private Long id;

    private BigDecimal balance;

    private BigDecimal allowedMinus;

    private BigDecimal limitWithdraw;

    private UserDTO user;
}
