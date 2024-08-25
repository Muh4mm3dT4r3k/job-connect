package com.mohamed.jobconnectv2.auth.dto;

import java.io.Serializable;


public record UserLoginResponse(String accessToken) implements Serializable {
}