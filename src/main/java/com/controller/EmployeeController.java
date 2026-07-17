package com.controller;

import com.model.Customer;
import com.serviceimpl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeServiceImpl employeeService ;

    @GetMapping()
    public List<Customer> getAllCustomersApplication() {
        return employeeService.getAllCustomersApplication() ;
    }

}
