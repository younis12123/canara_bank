package com.service;

import com.dto.AddCustomerRequestDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface CustomerService {

    public String addCustomer(AddCustomerRequestDto addCusomerRequestDto, Long branchId ) ;
}
