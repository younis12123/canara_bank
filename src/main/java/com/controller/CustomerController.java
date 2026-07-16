package com.controller;

import com.dto.AddCusomerRequestDto;
import com.serviceimpl.CustomerServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceimpl customerServiceimpl ;

    @PostMapping("/apply")
    public String addCustomer(@RequestBody AddCusomerRequestDto addCusomerRequestDto) {
        return customerServiceimpl.addCustomer(addCusomerRequestDto) ;
    }





}
