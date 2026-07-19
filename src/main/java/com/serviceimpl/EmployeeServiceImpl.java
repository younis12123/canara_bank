package com.serviceimpl;

import com.config.SecurityConfig;
import com.dao.CustomerRepository;
import com.dao.UserRepository;
import com.enums.CustomerStatus;
import com.mapper.UserMapper;
import com.model.Customer;
import com.model.Users;
import com.service.EmployeeService;
import com.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final CustomerRepository customerRepository ;

    private final UserRepository userRepository ;

    private final MailService mailService ;

    private final SecurityConfig securityConfig ;


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
        System.out.println(tempPassword);
        String encodedPassword = securityConfig.passwordEncoder().encode(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user) ;

        mailService.sendApprovalMail(customer.getFirstName(),user.getUserName(),tempPassword,customer.getEmail(),"bhargavvaddi09@gmail.com");

        return "Customer "+ customerId + " have been created send a email" ;
    }

    @Override
    public Customer rejectCustomer(Long id) {
        return null;
    }
}

