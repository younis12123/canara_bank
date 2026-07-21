package com.mapper;

import com.dto.AddCustomerRequestDto;
import com.dto.AddressDto;
import com.model.Address;
import com.model.Customer;

import java.time.LocalDateTime;


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


}

