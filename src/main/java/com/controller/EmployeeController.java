package com.controller;

import com.dto.AddBranchRequestDto;
import com.dto.AddEmployeeRequestDto;
import com.model.Branch;
import com.model.Customer;
import com.service.EmployeeService;
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

    @GetMapping("/applications")
    public List<Customer> viewAllCustomersApplication() {
        return employeeService.viewAllCustomersApplication() ;
    }

    @PatchMapping("applications/{customerId}/approve")
    public ResponseEntity<String> approveCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(employeeService.approveCustomer(customerId));
    }

    @PatchMapping("applications/{customerId}/reject")
    public ResponseEntity<String> rejectCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(employeeService.rejectCustomer(customerId));
    }

    @GetMapping ("/branches")
    public List<Branch> viewAllBranches(){ return employeeService.viewAllBranches() ;}

    @PostMapping("/branches/addbranch")
    public  String addNewBranch(@RequestBody AddBranchRequestDto addBranchRequestDto){
        return employeeService.addNewBranch(addBranchRequestDto);
    }

    @DeleteMapping("/branches/{branchId}")
    public ResponseEntity<String> deleteBranch(@PathVariable Long branchId){
        return ResponseEntity.ok(employeeService.deleteBranch(branchId));
    }

    @PostMapping("/addnewemployee/{branchId}")
    public String addNewEmployee(@RequestBody AddEmployeeRequestDto addEmployeeRequestDto ,@PathVariable Long branchId) {
        return employeeService.addNewEmployee(addEmployeeRequestDto ,branchId);
    }
}
