package com.mapper;

import com.enums.Role;
import com.model.Customer;
import com.model.Users;

import java.time.LocalDateTime;

public class UserMapper {

    public static Users toUser(Customer customer){
        return Users.builder().userName(customer.getCustomerNumber()).
                role(Role.CUSTOMER).createdAt(LocalDateTime.now()).
                fullName(customer.getFullName()).
                updatedAt(LocalDateTime.now()).build() ;
    }

}
