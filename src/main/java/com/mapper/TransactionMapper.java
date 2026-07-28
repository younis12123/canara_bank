package com.mapper;

import com.dto.TransactionRequestDto;
import com.dto.TransactionResponseDto;
import com.enums.TransactionStatus;
import com.model.Transaction;
import com.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

public class TransactionMapper {
    public static Transaction toTransaction(TransactionRequestDto requestDto) {
        return Transaction.builder().transactionReference(Utils.generateTransactionRef()).
                transactionType(requestDto.getTransactionType()).
                amount(requestDto.getAmount()).
                status(TransactionStatus.SUCCESS).
                transactionDate(LocalDateTime.now()).
                createdBy(SecurityContextHolder
                .getContext()
                .getAuthentication().getName()).
                createdAt(LocalDateTime.now()).build() ;
    }

    public static TransactionResponseDto toTranscationResponseDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .transactionReference(transaction.getTransactionReference())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .status(transaction.getStatus()).
                transactionDate(transaction.getTransactionDate()).build() ;
    }

}
