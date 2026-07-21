package com.controller;

import com.dto.AddCustomerRequestDto;
import com.serviceimpl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceimpl ;

    @PostMapping("/apply/{branchId}")
    public String addCustomer(@RequestBody AddCustomerRequestDto addCustomerRequestDto ,@PathVariable Long branchId ) {
        return customerServiceimpl.addCustomer(addCustomerRequestDto , branchId ) ;
    }





}
