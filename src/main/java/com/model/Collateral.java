package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collateral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;        // e.g., PROPERTY, VEHICLE, GOLD
    private double estimatedValue;
    private String description; // details like address, reg number

    @ManyToOne
    @JoinColumn(name = "loan_id")
    @JsonIgnore
    private Loan loan;

}
