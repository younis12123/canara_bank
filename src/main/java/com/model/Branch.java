package com.model;

import com.enums.BranchStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @JsonManagedReference
    private Address address;

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
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(
            mappedBy = "branch",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    @JsonIgnore
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
