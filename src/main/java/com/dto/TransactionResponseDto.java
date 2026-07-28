package com.dto;

import com.enums.TransactionStatus;
import com.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto{

        private String transactionReference;
        private TransactionType transactionType;
        private BigDecimal amount;
        private TransactionStatus status;
        private LocalDateTime transactionDate;

}
