package com.mapper;

import com.dto.*;
import com.enums.LoanStatus;
import com.enums.Role;
import com.model.*;
import com.util.Utils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerMapper {

    public static Customer toEntity(AddCustomerRequestDto addCusomerRequestDto) {
        Customer customer = Customer.builder().firstName(addCusomerRequestDto.getFirstName()).
                middleName(addCusomerRequestDto.getMiddleName()).
                lastName(addCusomerRequestDto.getLastName()).
                gender(addCusomerRequestDto.getGender()).
                dateOfBirth(addCusomerRequestDto.getDateOfBirth()).
                aadhaarNumber(addCusomerRequestDto.getAadhaarNumber()).
                panNumber(addCusomerRequestDto.getPanNumber()).
                email(addCusomerRequestDto.getEmail()).
                phoneNumber(addCusomerRequestDto.getPhoneNumber())
                .occupation(addCusomerRequestDto.getOccupation()).
                annualIncome(addCusomerRequestDto.getAnnualIncome()).
                fullName(addCusomerRequestDto.getFirstName()+" "+
                        addCusomerRequestDto.getMiddleName()+ " "+
                        addCusomerRequestDto.getLastName()).
                address(CustomerMapper.buildAddressFromRequest(addCusomerRequestDto.getAddress())).build();

        return customer;

    }

    public static Address buildAddressFromRequest(AddressDto dto) {
        return Address.builder()
                .addressType(dto.getAddressType())
                .houseNo(dto.getHouseNo())
                .street(dto.getStreet())
                .landmark(dto.getLandmark())
                .area(dto.getArea())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .state(dto.getState())
                .country(dto.getCountry())
                .postalCode(dto.getPostalCode())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

    }

    public static Users toUser(Customer customer){
        return Users.builder().userName(customer.getCustomerNumber()).
                role(Role.CUSTOMER).createdAt(LocalDateTime.now()).
                fullName(customer.getFullName()).
                email(customer.getEmail()).
                phoneNumber(customer.getPhoneNumber()).
                updatedAt(LocalDateTime.now()).build() ;
    }


    public static Loan toLoan(LoanRequestDto loanRequestDto) {
        Loan loan = Loan.builder().amount(loanRequestDto.getAmount()).
                tenureMonths(loanRequestDto.getTenureMonths()).
                loanType(loanRequestDto.getLoanType()).
                interestRate(Utils.determineInterestRate(loanRequestDto.getLoanType())).
                status(LoanStatus.PENDING).
                build();
        loan.setCollaterals((loanRequestDto.getCollateral().stream()
                .map(dto -> toCollateral(dto, null)).toList())) ;

        loan.setEmiAmount(Utils.calculateEmi(loanRequestDto.getAmount(),loan.getInterestRate(),
                loanRequestDto.getTenureMonths()));

        loan.setDocumentsSubmitted(
                loanRequestDto.getCollateral() != null && !loanRequestDto.getCollateral().isEmpty()
        );

        return loan ;
    }

    public static Collateral toCollateral(Collateral collateral, Loan loan) {
        return Collateral.builder()
                .type(collateral.getType())
                .estimatedValue(collateral.getEstimatedValue())
                .description(collateral.getDescription())
                .loan(loan)
                .build();
    }

    public static LoanResponseDto toLoanResponseDto(Loan loan) {
        return LoanResponseDto.builder()
                .customerNumber(loan.getCustomer().getCustomerNumber()) // assuming Loan has Customer relation
                .amount(loan.getAmount())
                .tenureMonths(loan.getTenureMonths())
                .loanType(loan.getLoanType())
                .interestRate(loan.getInterestRate())
                .emiAmount(loan.getEmiAmount())
                .status(loan.getStatus())
                .documentsSubmitted(loan.isDocumentsSubmitted())
                .build();
    }

    public static LoanStatusResponseDto toLoanStatusResponseDto(Customer customer, Loan loan) {
        return LoanStatusResponseDto.builder()
                .loanId(loan.getId())
                .customerId(customer.getCustomerNumber())
                .status(loan.getStatus())
                .amount(loan.getAmount())
                .emiAmount(loan.getEmiAmount())
                .interestRate(loan.getInterestRate())
                .build();
    }

    public static List<LoanStatusResponseDto> toLoanStatusResponseDtoList(Customer customer) {
        return customer.getLoans().stream()
                .map(loan -> toLoanStatusResponseDto(customer, loan))
                .collect(Collectors.toList());
    }

    public static ProfileResponseDto toProfileResponseDto(Customer customer) {
        return ProfileResponseDto.builder()
                .customerNumber(customer.getCustomerNumber())
                .firstName(customer.getFirstName())
                .middleName(customer.getMiddleName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .dateOfBirth(customer.getDateOfBirth())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(toAddressDto(customer.getAddress()))
                .occupation(customer.getOccupation())
                .annualIncome(customer.getAnnualIncome())
                .kycStatus(customer.getKycStatus())
                .customerStatus(customer.getCustomerStatus())
                .branch(toBranchDto(customer.getBranch()))
                .build();
    }

    public static BranchResponseDto toBranchDto(Branch branch) {
        return BranchResponseDto.builder()
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .ifscCode(branch.getIfscCode())
                .email(branch.getEmail())
                .phoneNumber(branch.getPhoneNumber())
                .address(toAddressDto(branch.getAddress()))
                .branchStatus(branch.getBranchStatus())
                .build();
    }
    private static AddressDto toAddressDto(Address address) {

        return AddressDto.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .build();
    }


}

