package com.serviceimpl;

import com.dao.UserRepository;
import com.dto.LoginRequestDto;
import com.enums.Role;
import com.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepo;

    private final JWTService jwtService ;

    private final AuthenticationManager authentication;

    public String customerLogin(LoginRequestDto loginRequestDto) {

        System.out.println("service");

       Authentication auth =
                authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
        
       System.out.println(auth);

        if (auth.isAuthenticated()) {
            Users user = userRepo.findByUserName(loginRequestDto.getUserName());

            System.out.println(user);
            
            if(Role.CUSTOMER.equals(user.getRole())) {

                if (user.getFirstLogin()) { return "PASSWORD_CHANGE_REQUIRED"; }
                return jwtService.generateToken(loginRequestDto.getUserName(), user.getRole() , user.getFullName());
            }
            return " user is not authorised";
        }
        return " user is not authenticated";
    }

    public String employeeLogin(LoginRequestDto loginRequestDto) {

        Authentication auth =
                authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),loginRequestDto.getPassword()));
        if (auth.isAuthenticated()) {
            Users user = userRepo.findByUserName(loginRequestDto.getUserName());
            if(Role.EMPLOYEE.equals(user.getRole()) || Role.MANAGER.equals(user.getRole())){

                if (user.getFirstLogin()) { return "PASSWORD_CHANGE_REQUIRED"; }
                return jwtService.generateToken(loginRequestDto.getUserName(), user.getRole() , user.getFullName());
            }
            return " user is not authorised";
        }
        return " user is not authenticated";

    }

}
