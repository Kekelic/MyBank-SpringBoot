package com.exercise.bankaccounts.persistance.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "bank_accounts", schema = "bankAccounts")
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance = BigDecimal.valueOf(0);

    @Column(name = "allowed_minus")
    private BigDecimal allowedMinus = BigDecimal.valueOf(0);

    @Column(name = "limit_withdraw")
    private BigDecimal limitWithdraw = BigDecimal.valueOf(2000);

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_user_fk", referencedColumnName = "id", nullable = false)
    private User user;
}
