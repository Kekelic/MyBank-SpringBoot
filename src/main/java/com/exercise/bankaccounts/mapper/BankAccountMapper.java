package com.exercise.bankaccounts.mapper;

import com.exercise.bankaccounts.dto.BankAccountDTO;
import com.exercise.bankaccounts.persistance.model.BankAccount;
import com.exercise.bankaccounts.request.BankAccountRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface BankAccountMapper {

    BankAccountDTO modelToDTO(BankAccount bankAccount);

    BankAccount requestToModel(BankAccountRequest bankAccountRequest);

    List<BankAccountDTO> modelsToDTOS(List<BankAccount> bankAccounts);


}
