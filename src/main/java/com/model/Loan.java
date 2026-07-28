package com.model;
import com.enums.LoanStatus;
import com.enums.LoanType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private int tenureMonths;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private double interestRate;

    private LocalDate issueDate;

    private LocalDate maturityDate;

    private double emiAmount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private LoanStatus status =  LoanStatus.PENDING;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Collateral> collaterals;

    private boolean documentsSubmitted ;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
