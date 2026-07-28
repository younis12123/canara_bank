package com.serviceimpl;

import com.dao.BankAccountRepository;
import com.dao.CustomerRepository;
import com.dao.TranscationRepository;
import com.dto.TransactionRequestDto;
import com.dto.TransactionResponseDto;
import com.mapper.TransactionMapper;
import com.model.BankAccount;
import com.model.Customer;
import com.model.Transaction;
import com.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranscationServiceImpl implements TransactionService {

    private final TranscationRepository transcationRepository ;

    private final BankAccountRepository bankAccountRepository ;

    private final CustomerRepository customerRepository ;

    @Override
    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {

        Transaction transaction = TransactionMapper.toTransaction(requestDto);

        String auth = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByCustomerNumber(
                auth
        ).orElseThrow(() -> new RuntimeException("Customer not found for: " + auth));

        transaction.setCustomer(customer);

        transaction.setFromAccount(customer.getAccount());

        BankAccount reciverAccount = bankAccountRepository.findByAccountNumber(requestDto.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        reciverAccount.setBalance(reciverAccount.getBalance().add(requestDto.getAmount()));

        customer.getAccount().setBalance(customer.getAccount().getBalance().subtract(requestDto.getAmount()));

        bankAccountRepository.save(reciverAccount) ;

        transaction.setToAccount(reciverAccount);

        transcationRepository.save(transaction) ;

        return TransactionMapper.toTranscationResponseDto(transaction) ;
    }


}
