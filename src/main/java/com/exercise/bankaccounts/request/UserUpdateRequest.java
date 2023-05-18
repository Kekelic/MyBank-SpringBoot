package com.exercise.bankaccounts.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstName;

    private String lastName;

    private String address;

    private String phone;
}
