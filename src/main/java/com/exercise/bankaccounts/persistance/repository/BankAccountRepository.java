package com.exercise.bankaccounts.persistance.repository;

import com.exercise.bankaccounts.persistance.model.BankAccount;
import com.exercise.bankaccounts.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    List<BankAccount> findByUser(User user);

}
