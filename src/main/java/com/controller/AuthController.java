package com.controller;

import com.dto.LoginRequestDto;
import com.serviceimpl.LoginService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private LoginService loginService ;

    @PostMapping("/customer/login")
    public String custlogin(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }



}
