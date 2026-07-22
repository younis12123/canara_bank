package com.serviceimpl;

import com.dao.UserRepository;
import com.dto.LoginRequestDto;
import com.dto.VerifyOtpRequestDto;
import com.enums.Role;
import com.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepo;

    private final JWTService jwtService;

    private final AuthenticationManager authentication;

    private final MailService mailService;

    private final SmsService smsService;

    private final OtpService otpService;

    private final PasswordEncoder passwordEncoder;

//    public String customerLogin(LoginRequestDto loginRequestDto) {
//
////        System.out.println("service");
//
//       Authentication auth =
//                authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
//
////       System.out.println(auth);
//
//        if (auth.isAuthenticated()) {
//            Users user = userRepo.findByUserName(loginRequestDto.getUserName()).orElseThrow(()-> new RuntimeException("User not found with username or email: " + loginRequestDto.getUserName()));
//

    /// /            System.out.println(user);
//
//            if(Role.CUSTOMER.equals(user.getRole())) {
//
//                if (user.getFirstLogin()) { return "PASSWORD_CHANGE_REQUIRED"; }
//                return jwtService.generateToken(loginRequestDto.getUserName(), user.getRole() , user.getFullName());
//            }
//            return " user is not authorised";
//        }
//        return " user is not authenticated";
//    }

//    public String employeeLogin(LoginRequestDto loginRequestDto) {
//
//        Authentication auth =
//                authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),loginRequestDto.getPassword()));
//        if (auth.isAuthenticated()) {
//            Users user = userRepo.findByUserName(loginRequestDto.getUserName()).orElseThrow(()-> new RuntimeException("User not found with username or email: " + loginRequestDto.getUserName()));
//            if(Role.EMPLOYEE.equals(user.getRole()) || Role.MANAGER.equals(user.getRole())){
//
//                if (user.getFirstLogin()) { return "PASSWORD_CHANGE_REQUIRED"; }
//                return jwtService.generateToken(loginRequestDto.getUserName(), user.getRole() , user.getFullName());
//            }
//            return " user is not authorised";
//        }
//        return " user is not authenticated";
//
//    }
    public String customerLogin(LoginRequestDto loginRequestDto) {

        System.out.println("wait for authentication");

        try {
            Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));
            System.out.println("auth object");

            if (auth.isAuthenticated()) {
                System.out.println("after authenication");

                Users user = userRepo.findByUserName(loginRequestDto.getUserName()).orElseThrow(() -> new RuntimeException("User not found with username or email: " + loginRequestDto.getUserName()));

                System.out.println("after getting user");

                if (Role.CUSTOMER.equals(user.getRole())) {

                    if (user.getAccountNonLocked() == false) {

                        System.out.println("account locked");

                        if (user.getLockTime() != null && user.getLockTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                            user.setAccountNonLocked(true);
                            user.setFailedLoginAttempts((byte) 0);
                            userRepo.save(user);
                        } else {
                            throw new RuntimeException("Account locked. Try After 15 minutes");
                        }
                    }
                    if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {

                        System.out.println("password missmached");

                        user.setFailedLoginAttempts((byte) (user.getFailedLoginAttempts() + 1));
                        if (user.getFailedLoginAttempts() >= 5) {
                            user.setAccountNonLocked(false);
                            user.setLockTime(LocalDateTime.now());
                        }
                        userRepo.save(user);
                        throw new RuntimeException("Invalid Credential");
                    }

                    System.out.println(" wait to send a mail");

                    user.setFailedLoginAttempts((byte) 0);

                    userRepo.save(user);

                    String otp = otpService.generateOtp(loginRequestDto.getUserName());

                    System.out.println("generated otp" + otp);

                    smsService.sendOtp(user.getPhoneNumber(), otp);

                    System.out.println("otp sended");

                    return "OTP sent";
                }
                return " user is not authorised";
            }

        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            System.out.println("Bad credentials: " + e.getMessage());
            return "Invalid username or password";

        } catch (org.springframework.security.authentication.LockedException e) {
            System.out.println("Account locked: " + e.getMessage());
            return "Account locked. Try after 15 minutes";

        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return "Authentication error";
        }

        return " user is not athenticated" ;
    }


    public String employeeLogin(LoginRequestDto loginRequestDto) {

        try {

            Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword()));

            if (auth.isAuthenticated()) {
                Users user = userRepo.findByUserName(loginRequestDto.getUserName()).orElseThrow(() -> new RuntimeException("User not found with username or email: " + loginRequestDto.getUserName()));
                if (Role.EMPLOYEE.equals(user.getRole()) || Role.MANAGER.equals(user.getRole())) {

                    if (user.getAccountNonLocked() == false) {
                        if (user.getLockTime() != null && user.getLockTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                            user.setAccountNonLocked(true);
                            user.setFailedLoginAttempts((byte) 0);
                            userRepo.save(user);
                        } else {
                            throw new RuntimeException("Account locked. Try After 15 minutes");
                        }
                    }
                    if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                        user.setFailedLoginAttempts((byte) (user.getFailedLoginAttempts() + 1));
                        if (user.getFailedLoginAttempts() >= 5) {
                            user.setAccountNonLocked(false);
                            user.setLockTime(LocalDateTime.now());
                        }
                        userRepo.save(user);
                        throw new RuntimeException("Invalid Credential");
                    }
                    user.setFailedLoginAttempts((byte) 0);
                    userRepo.save(user);
                    String otp = otpService.generateOtp(loginRequestDto.getUserName());
                    smsService.sendOtp(user.getPhoneNumber(), otp);
                    mailService.sendOtp(user.getEmail(), otp);
                    return "OTP sent";
                }
                return " user is not authorised";
            }
        } catch (org.springframework.security.authentication.BadCredentialsException e) {
            System.out.println("Bad credentials: " + e.getMessage());
            return "Invalid username or password";

        } catch (org.springframework.security.authentication.LockedException e) {
            System.out.println("Account locked: " + e.getMessage());
            return "Account locked. Try after 15 minutes";

        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return "Authentication error";
        }

        return " user is not authenticated";
    }

    public ResponseEntity<?> verifyOtp(VerifyOtpRequestDto request) {
        if (!otpService.verifyOtp(request.getUserName(), request.getOtp())) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        Users user = userRepo.findByUserName(request.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getUserName(),
                user.getRole(),
                user.getFullName()
        );

        return ResponseEntity.ok(token);
    }

}
