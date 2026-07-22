package com.config ;

import java.io.IOException;

import com.dao.UserRepository;
import com.model.Users;
import com.serviceimpl.MailService;
import com.serviceimpl.OtpService;
import com.serviceimpl.SmsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OtpLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final SmsService smsService;
    private final OtpService otpService;
    private final MailService mailService;
    private final UserRepository userRepo;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userName = authentication.getName();
        Users user = userRepo.findByUserName(userName).orElseThrow(()-> new RuntimeException("User not found"));
        String otp =  otpService.generateOtp(userName);
        if(user.getPhoneNumber() != null) {
            smsService.sendOtp(user.getPhoneNumber(), otp);
        }
        if(user.getEmail() != null) {
            mailService.sendOtp(user.getEmail(), otp);
        }
    }
}