package com.dto;

import com.enums.TransactionType;
import com.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRequestDto {

        private String accountNumber;
        private TransactionType transactionType;
        private BigDecimal amount;

}
