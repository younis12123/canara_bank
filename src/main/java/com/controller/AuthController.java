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
    public String custmoerLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.customerLogin(loginRequestDto);
    }

    @PostMapping("/employee/login")
    public String EmployeeLogin(@RequestBody LoginRequestDto loginRequestDto)  {
        return loginService.employeeLogin(loginRequestDto) ;
    }


}
