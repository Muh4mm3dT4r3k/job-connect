package com.mohamed.jobconnectv2.auth;

import com.mohamed.jobconnectv2.auth.dto.UserLoginRequest;
import com.mohamed.jobconnectv2.auth.dto.UserLoginResponse;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterRequest;
import com.mohamed.jobconnectv2.auth.dto.UserRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;



    @PostMapping("/register")
    public UserRegisterResponse register(@RequestBody @Valid UserRegisterRequest request){
        return authenticationService.register(request);
    }


    @PostMapping("/login")
    public UserLoginResponse loginResponse(@RequestBody @Valid UserLoginRequest request){
        return authenticationService.login(request);
    }
}
