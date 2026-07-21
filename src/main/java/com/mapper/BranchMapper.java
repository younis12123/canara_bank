package com.mapper;

import com.dto.AddBranchRequestDto;
import com.model.Branch;

import java.time.LocalDateTime;

public class BranchMapper {

    public static Branch toBranch(AddBranchRequestDto addBranchRequestDto){
        return Branch.builder().branchCode(addBranchRequestDto.getBranchCode()).
                branchName(addBranchRequestDto.getBranchName()).
                ifscCode(addBranchRequestDto.getIfscCode()).
                email(addBranchRequestDto.getEmail()).
                phoneNumber(addBranchRequestDto.getPhoneNumber()).
                address(CustomerMapper.buildAddressFromRequest(addBranchRequestDto.getAddress())).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).build() ;
    }
}
