package com.serviceimpl;

import com.dao.CustomerRepository;
import com.dao.UserRepository;
import com.enums.CustomerStatus;
import com.mapper.UserMapper;
import com.model.Customer;
import com.model.Employee;
import com.model.Users;
import com.service.EmployeeService;
import com.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerRepository customerRepository ;

    private final UserRepository userRepository ;

    @Override
    public List<Customer> viewAllActiveCustomers() {
        return customerRepository.findByCustomerStatus(CustomerStatus.ACTIVE) ;
    }

    @Override
    public List<Customer> viewAllCustomersApplication() {
        return customerRepository.findByCustomerStatus(CustomerStatus.REQUESTED) ;
    }

    @Override
    public String approveCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NoSuchElementException(" customer is not found"+ customerId)) ;
        customer.setCustomerStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer) ;
        Users user = UserMapper.toUser(customer);
        String tempPassword = CustomerUtils.generateTempPassword();
        String encodedPassword = CustomerUtils.encodePassword(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user) ;
        return "Customer "+ customerId + " have been created send a email" ;
    }

    @Override
    public Customer rejectCustomer(Long id) {
        return null;
    }
}

