package com.dto;

import com.enums.LoanStatus;
import com.enums.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDto {

    private String customerNumber;

    private double amount;

    private int tenureMonths;

    private LoanType loanType;

    private double interestRate;

    private double emiAmount;

    private LoanStatus status;

    private boolean documentsSubmitted;

}
