package com.serviceimpl;

import com.dao.CustomerRepository;
import com.enums.CustomerStatus;
import com.model.Customer;
import com.model.Employee;
import com.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerRepository customerRepository ;

    @Override
    public List<Customer> getAllCustomersApplication() {
        return customerRepository.findByCustomerStatus(CustomerStatus.REQUESTED) ;
    }
}
