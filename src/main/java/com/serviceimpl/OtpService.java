package com.serviceimpl;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final StringRedisTemplate redisTemplate ;

    public String generateOtp(String userName){
        String otp1 = String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));
        redisTemplate.opsForValue().set("otp:"+userName,otp1, Duration.ofMinutes(55));
        return otp1 ;
    }

    public boolean verifyOtp(String userName, String otp) {
        String storedOtp = redisTemplate.opsForValue().get("otp:"+userName);
        if(storedOtp== null) {
            return false;
        }
        return storedOtp.equals(otp) ;
    }

}
