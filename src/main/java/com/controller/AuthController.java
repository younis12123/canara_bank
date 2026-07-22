package com.controller;

import com.dto.LoginRequestDto;
import com.dto.VerifyOtpRequestDto;
import com.serviceimpl.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private LoginService loginService ;

    @PostMapping("/customer/login")
    public String custmoerLogin(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println("controller");
        return loginService.customerLogin(loginRequestDto);
    }

    @PostMapping("/employee/login")
    public String EmployeeLogin(@RequestBody LoginRequestDto loginRequestDto)  {
        return loginService.employeeLogin(loginRequestDto) ;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequestDto request) {
        return loginService.verifyOtp(request);
    }


}
