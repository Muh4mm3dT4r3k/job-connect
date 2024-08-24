package com.mohamed.jobconnectv2.user.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;


public record UserRegisterRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "email is required")
        String email,
        @NotBlank(message = "password is required")
        String password) implements Serializable { }