package com.dao;

import com.enums.CustomerStatus;
import com.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    List<Customer> findByCustomerStatus(CustomerStatus status);

    Optional<Customer> findByCustomerNumber(String customerNumber);

}
