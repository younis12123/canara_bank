package com.dto;

import com.enums.LoanType;
import com.model.Collateral;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDto {

    private double amount;

    private int tenureMonths;

    private LoanType loanType;

    private List<Collateral> collateral;

}
