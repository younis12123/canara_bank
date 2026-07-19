package com.controller;

import com.model.Customer;
import com.service.EmployeeService;
import com.serviceimpl.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping()
    public List<Customer> viewAllActiveCustomers() {
        return employeeService.viewAllActiveCustomers() ;
    }

    @GetMapping("/addcustomer")
    public List<Customer> viewAllCustomersApplication() {
        return employeeService.viewAllCustomersApplication() ;
    }

    @PatchMapping("/{customerId}/approve")
    public ResponseEntity<String> approveCustomer(@PathVariable Long customerId ) {
        return ResponseEntity.ok(employeeService.approveCustomer(customerId));
    }

//    // ✅ Reject a specific application
//    @PatchMapping("/{id}/reject")
//    public ResponseEntity<Customer> rejectCustomer(@PathVariable Long customerId) {
//        return ResponseEntity.ok(employeeService.rejectCustomer(customerId));
//    }

}
