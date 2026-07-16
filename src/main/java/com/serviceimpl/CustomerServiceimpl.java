package com.serviceimpl;

import com.dao.CustomerRepository;
import com.dao.UserRepository;
import com.dto.AddCusomerRequestDto;
import com.enums.CustomerStatus;
import com.enums.Role;
import com.mapper.CustomerMapper;
import com.model.Customer;
import com.model.Users;
import com.service.CustomerService;
import com.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceimpl implements CustomerService {

    private final CustomerRepository customerRepository ;

    private final UserRepository userRepository ;

    @Override
    public String addCustomer(AddCusomerRequestDto addCusomerRequestDto) {
        Customer customer = CustomerMapper.toEntity(addCusomerRequestDto);
        customer.setCustomerNumber(CustomerUtils.generateCustomerNumber());
        customer.setCustomerStatus(CustomerStatus.REQUESTED);
        customerRepository.save(customer) ;

        Users user = new Users();
        user.setUserName(customer.getCustomerNumber());
        user.setRole(Role.CUSTOMER);
        user.setPassword();

        return "You Account have been created , please check email further Info"
    }
}
