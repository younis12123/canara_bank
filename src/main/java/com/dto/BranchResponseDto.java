package com.dto;

import com.enums.BranchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseDto {

        private String branchCode;
        private String branchName;
        private String ifscCode;
        private String email;
        private String phoneNumber;

        private AddressDto address;

        private BranchStatus branchStatus;

}
