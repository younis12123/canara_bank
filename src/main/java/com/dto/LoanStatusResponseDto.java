package com.dto;

import com.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanStatusResponseDto {

    private Long loanId;
    private String customerId;
    private LoanStatus status;
    private double amount;
    private double emiAmount;
    private double interestRate;

}
