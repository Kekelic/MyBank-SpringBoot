package com.exercise.bankaccounts.service;

import com.exercise.bankaccounts.dto.BankAccountDTO;
import com.exercise.bankaccounts.exception.BankAccountNotFoundException;
import com.exercise.bankaccounts.exception.UserNotFoundException;
import com.exercise.bankaccounts.mapper.BankAccountMapper;
import com.exercise.bankaccounts.persistance.model.BankAccount;
import com.exercise.bankaccounts.persistance.model.User;
import com.exercise.bankaccounts.persistance.repository.BankAccountRepository;
import com.exercise.bankaccounts.persistance.repository.UserRepository;
import com.exercise.bankaccounts.request.BankAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    private UserRepository userRepository;

    private BankAccountMapper bankAccountMapper;

    public List<BankAccountDTO> retrieveBankAccountsFromUser() {
        String username = getLoggedInUserName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        List<BankAccount> bankAccounts = bankAccountRepository.findByUser(user.get());
        return bankAccountMapper.modelsToDTOS(bankAccounts);
    }

    public BankAccountDTO retrieveSpecificBankAccount(int bankAccountId) {
        BankAccount bankAccount = getBankAccount(bankAccountId);
        return bankAccountMapper.modelToDTO(bankAccount);
    }

    public BigDecimal retrieveBalance(int bankAccountId) {
        return getBankAccount(bankAccountId).getBalance();
    }

    public void createNewBankAccount(BankAccountRequest bankAccountRequest) {
        String username = getLoggedInUserName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        BankAccount bankAccount = bankAccountMapper.requestToModel(bankAccountRequest);
        bankAccount.setUser(user.get());
        bankAccountRepository.save(bankAccount);
    }

    public Boolean depositMoney(int bankAccountId, BigDecimal amount) {
        BankAccount bankAccount = getBankAccount(bankAccountId);
        BigDecimal currentBalance = bankAccount.getBalance();
        bankAccount.setBalance(currentBalance.add(amount));
        bankAccountRepository.save(bankAccount);
        return true;
    }

    public Boolean withdrawMoney(int bankAccountId, BigDecimal amount) {
        BankAccount bankAccount = getBankAccount(bankAccountId);
        BigDecimal currentBalance = bankAccount.getBalance();
        BigDecimal limitWithdraw = bankAccount.getLimitWithdraw();
        BigDecimal allowedMinus = bankAccount.getAllowedMinus();

        BigDecimal balanceAfterWithdraw = currentBalance.subtract(amount);

        if (amount.compareTo(limitWithdraw) <= 0 && balanceAfterWithdraw.compareTo(allowedMinus) >= 0) {
            bankAccount.setBalance(balanceAfterWithdraw);
            bankAccountRepository.save(bankAccount);
            return true;
        }
        return false;

    }

    public Boolean transferMoney(int withdrawBankAccountId, int depositBankAccountId, BigDecimal amount) throws IllegalAccessException {
        if (withdrawMoney(withdrawBankAccountId, amount)) {
            Optional<BankAccount> depositBankAccount = bankAccountRepository.findById(depositBankAccountId);
            if (depositBankAccount.isEmpty()) {
                throw new BankAccountNotFoundException("Bank account not found!");
            }
            BigDecimal currentBalance = depositBankAccount.get().getBalance();
            depositBankAccount.get().setBalance(currentBalance.add(amount));
            bankAccountRepository.save(depositBankAccount.get());
            return true;
        }
        return false;

    }

    public void deleteBankAccount(int bankAccountId) {
        BankAccount bankAccount = getBankAccount(bankAccountId);
        bankAccount.setUser(null);
        bankAccountRepository.delete(bankAccount);
    }

    private BankAccount getBankAccount(int bankAccountId) {
        String username = getLoggedInUserName();
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(bankAccountId);
        if (bankAccount.isEmpty()) {
            throw new BankAccountNotFoundException("Bank account not found!");
        }
        return bankAccount.get();
    }

    private static String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("Username not found, please login in again!");
    }
}
