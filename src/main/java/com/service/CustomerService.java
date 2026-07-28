package com.service;

import com.dto.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CustomerService {

    public String addCustomer(AddCustomerRequestDto addCusomerRequestDto, Long branchId ) ;

    LoanResponseDto rasieLoan(LoanRequestDto loanRequestDto);

    List<LoanStatusResponseDto> loanStatus();

    ProfileResponseDto profile();
}
