package com.dto;

import com.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @NotBlank(message = "Address type is required")
    private AddressType addressType;

    @NotBlank(message = "House number is required")
    @Size(max = 20, message = "House number must be less than 20 characters")
    private String houseNo;

    @NotBlank(message = "Street is required")
    private String street;

    private String landmark;

    private String area;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal code is required")
    @Size(min = 6, max = 6, message = "Postal code must be 6 digits")
    private String postalCode;
}
