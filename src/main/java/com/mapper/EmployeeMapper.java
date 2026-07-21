package com.mapper;

import com.dto.AddEmployeeRequestDto;
import com.enums.Designation;
import com.enums.Role;
import com.model.Customer;
import com.model.Employee;
import com.model.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeMapper {

    public static Employee toEmployee(AddEmployeeRequestDto addEmployeeRequestDto){
        return Employee.builder().firstName(addEmployeeRequestDto.getFirstName()).
                middleName(addEmployeeRequestDto.getMiddleName()).
                lastName(addEmployeeRequestDto.getLastName()).
                gender(addEmployeeRequestDto.getGender()).
                dateOfBirth(addEmployeeRequestDto.getDateOfBirth()).
                email(addEmployeeRequestDto.getEmail()).
                phoneNumber(addEmployeeRequestDto.getPhoneNumber()).
                aadhaarNumber(addEmployeeRequestDto.getAadhaarNumber()).
                panNumber(addEmployeeRequestDto.getPanNumber()).
                designation(addEmployeeRequestDto.getDesignation()).
                department(addEmployeeRequestDto.getDepartment()).
                joiningDate(LocalDate.now()).
                fullName(addEmployeeRequestDto.getFirstName()+" "+
                        addEmployeeRequestDto.getMiddleName()+" "+
                        addEmployeeRequestDto.getLastName()).
                salary(addEmployeeRequestDto.getSalary()).
                createdAt(LocalDateTime.now()).build() ;

    }

    public static Users toUser(Employee employee) {

        Users user = Users.builder().fullName(employee.getFullName()).
                createdAt(LocalDateTime.now()).build();

        if (employee.getDesignation() == Designation.BRANCH_MANAGER) {
            user.setRole(Role.MANAGER);
        } else {
            user.setRole(Role.EMPLOYEE);
        }

        return user ;
    }
}
