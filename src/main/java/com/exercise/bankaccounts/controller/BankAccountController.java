package com.exercise.bankaccounts.controller;

import com.exercise.bankaccounts.dto.BankAccountDTO;
import com.exercise.bankaccounts.request.BankAccountRequest;
import com.exercise.bankaccounts.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RequestMapping(value = "/bank-accounts")
@RestController
public class BankAccountController {

    private BankAccountService bankAccountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BankAccountDTO> retrieveBankAccountsFromUser() {
        return bankAccountService.retrieveBankAccountsFromUser();

    }

    @GetMapping(value = "/{bankAccountId}")
    @ResponseStatus(HttpStatus.OK)
    public BankAccountDTO retrieveSpecificBankAccount(@PathVariable int bankAccountId) {
        return bankAccountService.retrieveSpecificBankAccount(bankAccountId);
    }

    @GetMapping(value = "/{bankAccountId}/balance")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal retrieveBalance(@PathVariable int bankAccountId) {
        return bankAccountService.retrieveBalance(bankAccountId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
        bankAccountService.createNewBankAccount(bankAccountRequest);
    }

    @PatchMapping(value = "/{bankAccountId}/deposit")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean depositMoney(@PathVariable int bankAccountId, @RequestParam BigDecimal amount) {
        return bankAccountService.depositMoney(bankAccountId, amount);
    }

    @PatchMapping(value = "/{bankAccountId}/withdraw")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean withdrawMoney(@PathVariable int bankAccountId, @RequestParam BigDecimal amount) {
        return bankAccountService.withdrawMoney(bankAccountId, amount);
    }

    @PatchMapping(value = "/{withdrawBankAccountId}/transfer/{depositBankAccountId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Boolean transferMoney(@PathVariable int withdrawBankAccountId, @PathVariable int depositBankAccountId, @RequestParam BigDecimal amount) throws IllegalAccessException {
        return bankAccountService.transferMoney(withdrawBankAccountId, depositBankAccountId, amount);
    }

    @DeleteMapping(value = "/{bankAccountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBankAccount(@PathVariable int bankAccountId) {
        bankAccountService.deleteBankAccount(bankAccountId);
    }


}
