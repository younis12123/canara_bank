package com.mapper;

import com.enums.AccountType;
import com.model.BankAccount;
import com.model.Branch;
import com.model.Customer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BankAccountMapper {


    public static BankAccount toBankAccount(Customer customer) {

        return BankAccount.builder()
                .customer(customer).
                accountType(AccountType.SAVINGS)
                .openedDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .balance(BigDecimal.ZERO)
                .minimumBalance(BigDecimal.ZERO)// default balance
                .build();
    }
}
