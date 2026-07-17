package com.serviceimpl;

import com.dao.CustomerRepository;
import com.dao.UserRepository;
import com.dto.AddCustomerRequestDto;
import com.enums.CustomerStatus;
import com.enums.KycStatus;
import com.mapper.CustomerMapper;
import com.model.Address;
import com.model.Customer;
import com.service.CustomerService;
import com.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository ;

    @Override
    public String addCustomer(AddCustomerRequestDto addCustomerRequestDto) {
        Customer customer = CustomerMapper.toEntity(addCustomerRequestDto);
        customer.setCustomerNumber(CustomerUtils.generateCustomerNumber());
        customer.setCustomerStatus(CustomerStatus.REQUESTED);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setKycStatus(KycStatus.PENDING);
        customer.getAddress().setCustomer(customer);
        customerRepository.save(customer) ;

        return "You Account have been created , please check email further Info" ;
    }
}
