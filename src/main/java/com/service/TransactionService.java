package com.service;

import com.dto.TransactionRequestDto;
import com.dto.TransactionResponseDto;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);
}
