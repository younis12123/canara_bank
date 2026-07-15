package com.model;

import com.enums.BranchStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "branch_code",
            nullable = false,
            unique = true,
            updatable = false,
            length = 10)
    private String branchCode;

    @Column(name = "branch_name",
            nullable = false,
            length = 100)
    private String branchName;

    @Column(name = "ifsc_code",
            nullable = false,
            unique = true,
            length = 11)
    private String ifscCode;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number",
            nullable = false,
            length = 10)
    private String phoneNumber;

    @Column(name = "house_no")
    private String houseNo;

    private String street;

    private String landmark;

    private String area;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(name = "postal_code",
            nullable = false)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "branch_status",
            nullable = false)
    private BranchStatus branchStatus = BranchStatus.ACTIVE;

    @OneToMany(
            mappedBy = "branch",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(
            mappedBy = "branch",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
