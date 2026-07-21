package com.service;

import com.dto.AddBranchRequestDto;
import com.enums.CustomerStatus;
import com.model.Branch;
import com.model.Customer;

import java.util.List;

public interface EmployeeService {

    List<Customer> viewAllActiveCustomers();

    List<Customer> viewAllCustomersApplication();

    String approveCustomer(Long customerId);

    String rejectCustomer(Long customerId);

    List<Branch> viewAllBranches();

    String addNewBranch(AddBranchRequestDto addBranchRequestDto);

    String deleteBranch(Long branchId);

    ;
}
