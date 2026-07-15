package com.model;

import com.enums.Department;
import com.enums.Designation;
import com.enums.EmployeeStatus;
import com.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    // Business Employee ID
    @Column(name = "employee_code",
            nullable = false,
            unique = true,
            updatable = false,
            length = 20)
    private String employeeCode;

    @Column(nullable = false)
    private  String password ;

    // Personal Details
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number",
            nullable = false,
            unique = true,
            length = 10)
    private String phoneNumber;

    // Identity
    @Column(name = "aadhaar_number",
            nullable = false,
            unique = true,
            length = 12)
    private String aadhaarNumber;

    @Column(name = "pan_number",
            nullable = false,
            unique = true,
            length = 10)
    private String panNumber;

    // Employment Details
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Designation designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salary;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false)
    private EmployeeStatus employeeStatus = EmployeeStatus.ACTIVE;

    // Self Reference (Manager)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    // Audit Fields
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
