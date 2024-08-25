package com.mohamed.jobconnectv2.auth;

import com.mohamed.jobconnectv2.auth.dto.UserLoginRequest;
import com.mohamed.jobconnectv2.auth.dto.UserLoginResponse;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterRequest;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterResponse;

public interface AuthenticationService {
    UserRegisterResponse register(UserRegisterRequest request);
    UserLoginResponse login(UserLoginRequest request);
}
