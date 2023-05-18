package com.exercise.bankaccounts.controller;


import com.exercise.bankaccounts.dto.UserDTO;
import com.exercise.bankaccounts.request.UserCreateRequest;
import com.exercise.bankaccounts.request.UserUpdateRequest;
import com.exercise.bankaccounts.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping(value = "/user")
@RestController
@CrossOrigin( origins = "http://localhost:4200")
public class UserController {

    private UserService userService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO retrieveUserDetails() {
        return userService.retrieveUserDetails();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        userService.createNewUser(userCreateRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserDetails(@RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUserDetails(userUpdateRequest);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser() {
        userService.deleteUser();
    }

}
