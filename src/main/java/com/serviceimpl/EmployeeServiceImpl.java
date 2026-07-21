package com.serviceimpl;

import com.config.SecurityConfig;
import com.dao.BankAccountRepository;
import com.dao.BranchRepository;
import com.dao.CustomerRepository;
import com.dao.UserRepository;
import com.dto.AddBranchRequestDto;
import com.enums.CustomerStatus;
import com.enums.KycStatus;
import com.mapper.BankAccountMapper;
import com.mapper.BranchMapper;
import com.mapper.UserMapper;
import com.model.BankAccount;
import com.model.Branch;
import com.model.Customer;
import com.model.Users;
import com.service.EmployeeService;
import com.util.CustomerUtils;
import jakarta.persistence.EntityManager;
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

    private final BranchRepository branchRepository ;

    private final BankAccountRepository bankAccountRepository ;


    @Override
    public List<Customer> viewAllActiveCustomers() {
        return customerRepository.findByCustomerStatus(CustomerStatus.ACTIVE) ;
    }

    @Override
    public List<Customer> viewAllCustomersApplication() {
        return customerRepository.findByCustomerStatus(CustomerStatus.REQUESTED) ;
    }

    @Override
    public String approveCustomer(Long customerId ) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NoSuchElementException(" customer is not found"+ customerId)) ;
        customer.setCustomerStatus(CustomerStatus.ACTIVE);
        customer.setKycStatus(KycStatus.VERIFIED);
        customerRepository.save(customer) ;

        Users user = UserMapper.toUser(customer);
        String tempPassword = CustomerUtils.generateTempPassword();
        String encodedPassword = securityConfig.passwordEncoder().encode(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user) ;

        BankAccount bankAccount = BankAccountMapper.toBankAccount(customer) ;
        bankAccount.setBranch(customer.getBranch());

        // need to use postgrase sql for sequence
        bankAccount.setAccountNumber(CustomerUtils.generateAccountNumber());
        BankAccount account = bankAccountRepository.save(bankAccount);

        mailService.sendApprovalMail(customer.getFirstName(),user.getUserName(),tempPassword,customer.getEmail(),"shaik.younis1212@gmail.com");

        return "Customer "+ customerId + " have been created send a email" ;
    }

    @Override
    public String rejectCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new NoSuchElementException(" customer is not found"+ customerId)) ;
        customer.setCustomerStatus(CustomerStatus.REJECTED);
        customer.setKycStatus(KycStatus.REJECTED);
        customerRepository.save(customer) ;

        mailService.sendRejectedMail(customer.getFirstName(),customer.getEmail(),"shaik.younis1212@gmail.com");

        return "Customer with ID " + customerId + " has been rejected. " ;
    }

    @Override
    public List<Branch> viewAllBranches() { return branchRepository.findAll() ; }

    @Override
    public String addNewBranch(AddBranchRequestDto addBranchRequestDto) {
        Branch branch = BranchMapper.toBranch(addBranchRequestDto) ;
        branchRepository.save(branch);
        return " new branch is successfully added" ;
    }

    @Override
    public String deleteBranch(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + branchId));
        branchRepository.delete(branch);
        return "Branch with ID " + branchId + " deleted successfully";
    }
}