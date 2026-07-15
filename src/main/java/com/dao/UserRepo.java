package com.dao;

import com.model.Users;
import com.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,Long> {

    Users findByUserName(String userName);
}
