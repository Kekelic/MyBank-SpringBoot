package com.exercise.bankaccounts.service;

import com.exercise.bankaccounts.dto.UserDTO;
import com.exercise.bankaccounts.exception.UserAlreadyExistException;
import com.exercise.bankaccounts.exception.UserNotFoundException;
import com.exercise.bankaccounts.mapper.UserMapper;
import com.exercise.bankaccounts.persistance.model.BankAccount;
import com.exercise.bankaccounts.persistance.model.User;
import com.exercise.bankaccounts.persistance.repository.BankAccountRepository;
import com.exercise.bankaccounts.persistance.repository.UserRepository;
import com.exercise.bankaccounts.request.UserCreateRequest;
import com.exercise.bankaccounts.request.UserUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BankAccountRepository bankAccountRepository;

    private UserMapper userMapper;

    public UserDTO retrieveUserDetails() {
        String username = getLoggedInUserName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        return userMapper.modelToDTO(user.get());
    }

    public void createNewUser(UserCreateRequest userCreateRequest) {
        if (checkIfUserExist(userCreateRequest.getUsername())) {
            throw new UserAlreadyExistException("User already exists for this username!");
        }
        String password = userCreateRequest.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        userCreateRequest.setPassword(encodedPassword);
        userRepository.save(userMapper.createRequestToModel(userCreateRequest));
    }

    public void updateUserDetails(UserUpdateRequest userUpdateRequest) {
        String username = getLoggedInUserName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }
        updateData(user.get(), userUpdateRequest);

    }

    private void updateData(User user, UserUpdateRequest userUpdateRequest) {
        String firstName = userUpdateRequest.getFirstName();
        String lastName = userUpdateRequest.getLastName();
        String address = userUpdateRequest.getAddress();
        String phone = userUpdateRequest.getPhone();
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (address != null) {
            user.setAddress(address);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        userRepository.save(user);
    }

    public void deleteUser() {
        String username = getLoggedInUserName();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found!");
        }

        List<BankAccount> bankAccounts = bankAccountRepository.findByUser(user.get());
        bankAccounts.forEach(bankAccount -> bankAccountRepository.delete(bankAccount));
        userRepository.delete(user.get());
    }

    private boolean checkIfUserExist(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }


    private static String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new UsernameNotFoundException("Username not found, please login in again!");
    }


}
