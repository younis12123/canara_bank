package com.serviceimpl;

import com.dao.UserRepo;
import com.dto.LoginRequestDto;
import com.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepo userRepo;

    private final JWTService jwtService ;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authentication;

    public String login(LoginRequestDto loginRequestDto) {

        Authentication auth =
                authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
        if (auth.isAuthenticated()) {
            Users user = userRepo.findByUserName(loginRequestDto.getUserName());
            return jwtService.generateToken(loginRequestDto.getUserName(),user.getRole());
        }
        return " user is not authenticated";

    }

}
