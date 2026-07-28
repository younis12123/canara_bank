package com.controller;

import com.dto.*;
import com.service.CustomerService;
import com.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService ;

    private final TransactionService transactionService ;

    //apply
    @PostMapping("/apply/{branchId}")
    public String addCustomer(@RequestBody AddCustomerRequestDto addCustomerRequestDto ,@PathVariable Long branchId ) {
        return customerService.addCustomer(addCustomerRequestDto , branchId ) ;
    }

    // raise loan
    @PostMapping("/raiseloan")
    public LoanResponseDto raiseLoan(@RequestBody LoanRequestDto loanRequestDto){
        return customerService.rasieLoan(loanRequestDto);
    }


    // loan status
    @GetMapping("/status")
    public List<LoanStatusResponseDto> getLoanStatus() {
        System.out.println("controller");
        return customerService.loanStatus();
    }


    // create transcation

    @PostMapping("/transcation")
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto requestDto) {
        TransactionResponseDto responseDto = transactionService.createTransaction(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // my transcation

    // profile
    @GetMapping("/profile")
    public ProfileResponseDto profile() {
        return customerService.profile() ;
    }

}
