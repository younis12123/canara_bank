package com.serviceimpl;

import com.dao.BranchRepository;
import com.dao.CustomerRepository;
import com.dao.LoanRepository;
import com.dto.*;
import com.enums.CustomerStatus;
import com.enums.KycStatus;
import com.mapper.CustomerMapper;
import com.model.Branch;
import com.model.Collateral;
import com.model.Customer;
import com.model.Loan;
import com.service.CustomerService;
import com.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository ;

    private final BranchRepository branchRepository ;

    private final LoanRepository loanRepository ;

    @Override
    public String addCustomer(AddCustomerRequestDto addCustomerRequestDto,Long branchId ) {


        Customer customer = CustomerMapper.toEntity(addCustomerRequestDto);
        customer.setCustomerNumber(Utils.generateCustomerNumber());


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

    @Override
    public LoanResponseDto rasieLoan(LoanRequestDto loanRequestDto) {

//        Customer customer = customerRepository.findByCustomerNumber(SecurityContextHolder
//                .getContext()
//                .getAuthentication().getName()).orElseThrow(() ->  new RuntimeException("Customer not found")) ;

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(auth);

        Customer customer = customerRepository.findByCustomerNumber(
                auth
        ).orElseThrow(() -> new RuntimeException("Customer not found for: " + auth));


        Loan loan = CustomerMapper.toLoan(loanRequestDto);
        loan.setCustomer(customer) ;

        if (loan.getCollaterals() != null) {
            for (Collateral collateral : loan.getCollaterals()) {
                collateral.setLoan(loan);
            }
        }

        loanRepository.save(loan) ;

        return CustomerMapper.toLoanResponseDto(loan);
    }

    @Override
    public List<LoanStatusResponseDto> loanStatus() {

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(auth);

        Customer customer = customerRepository.findByCustomerNumber(
                auth
        ).orElseThrow(() -> new RuntimeException("Customer not found for: " + auth));
//
//        Customer customer = customerRepository.findByCustomerNumber(SecurityContextHolder
//                .getContext()
//                .getAuthentication().getName()).orElseThrow(() ->  new RuntimeException("Customer not found")) ;

        System.out.println("after customer fetch");

        return CustomerMapper.toLoanStatusResponseDtoList(customer);
    }

    @Override
    public ProfileResponseDto profile() {

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(auth);

        Customer customer = customerRepository.findByCustomerNumber(
                auth
        ).orElseThrow(() -> new RuntimeException("Customer not found for: " + auth));

        return CustomerMapper.toProfileResponseDto(customer);
    }
}
