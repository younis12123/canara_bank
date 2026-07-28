package com.dto;

import com.enums.CustomerStatus;
import com.enums.Gender;
import com.enums.KycStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {
    private String customerNumber;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;

    private AddressDto address;

    private String occupation;
    private BigDecimal annualIncome;
    private KycStatus kycStatus;
    private CustomerStatus customerStatus;

    private BranchResponseDto branch;
}
