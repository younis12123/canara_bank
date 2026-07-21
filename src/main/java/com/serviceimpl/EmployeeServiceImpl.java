package com.serviceimpl;

import com.config.SecurityConfig;
import com.dao.*;
import com.dto.AddBranchRequestDto;
import com.dto.AddEmployeeRequestDto;
import com.enums.CustomerStatus;
import com.enums.KycStatus;
import com.mapper.BankAccountMapper;
import com.mapper.BranchMapper;
import com.mapper.CustomerMapper;
import com.mapper.EmployeeMapper;
import com.model.*;
import com.service.EmployeeService;
import com.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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

    private final EmployeeRepository employeeRepository ;


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

        Users user = CustomerMapper.toUser(customer);
        String tempPassword = Utils.generateTempPassword();
        String encodedPassword = securityConfig.passwordEncoder().encode(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user) ;

        BankAccount bankAccount = BankAccountMapper.toBankAccount(customer) ;
        bankAccount.setBranch(customer.getBranch());

        // need to use postgrase sql for sequence
        bankAccount.setAccountNumber(Utils.generateAccountNumber());
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

    @Override
    public String addNewEmployee(AddEmployeeRequestDto addEmployeeRequestDto ,Long branchId) {
        Employee employee = EmployeeMapper.toEmployee(addEmployeeRequestDto) ;
        employee.setEmployeeCode(Utils.generateEmployeeNumber()) ;

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new NoSuchElementException("Branch not found with id: " + branchId));
        employee.setBranch(branch);

        if (addEmployeeRequestDto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(addEmployeeRequestDto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            employee.setManager(manager);
        } else {
            // no manager assigned
            employee.setManager(null);
        }

        employeeRepository.save(employee) ;

        Users user = EmployeeMapper.toUser(employee);
        user.setUserName(employee.getEmployeeCode());
        String tempPassword = Utils.generateTempPassword();
        String encodedPassword = securityConfig.passwordEncoder().encode(tempPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user) ;

        mailService.sendEmployeeApprovalMail(employee.getFirstName(),user.getUserName(),tempPassword,employee.getEmail(),"shaik.younis1212@gmail.com");

        return "Employee "+ addEmployeeRequestDto.getFirstName() + " have been created send a email" ;

    }
}