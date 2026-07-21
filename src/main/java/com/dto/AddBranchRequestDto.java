package com.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBranchRequestDto {

    @NotBlank
    private String branchCode;

    @NotBlank
    private String branchName;

    @NotBlank
    private String ifscCode;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    private AddressDto address;
}
