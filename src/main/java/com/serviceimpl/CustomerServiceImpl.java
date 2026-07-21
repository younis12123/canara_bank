package com.serviceimpl;

import com.dao.BranchRepository;
import com.dao.CustomerRepository;
import com.dto.AddCustomerRequestDto;
import com.enums.CustomerStatus;
import com.enums.KycStatus;
import com.mapper.CustomerMapper;
import com.model.Branch;
import com.model.Customer;
import com.service.CustomerService;
import com.util.CustomerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository ;

    private final BranchRepository branchRepository ;

    @Override
    public String addCustomer(AddCustomerRequestDto addCustomerRequestDto,Long branchId ) {


        Customer customer = CustomerMapper.toEntity(addCustomerRequestDto);
        customer.setCustomerNumber(CustomerUtils.generateCustomerNumber());


        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + branchId));
        customer.setBranch(branch);

        customer.setCustomerStatus(CustomerStatus.REQUESTED);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setKycStatus(KycStatus.PENDING);
        customer.getAddress().setCustomer(customer);
        customerRepository.save(customer) ;

        return "You Account have been created , please check email further Info" ;
    }
}
