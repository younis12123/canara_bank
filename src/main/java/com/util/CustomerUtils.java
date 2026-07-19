package com.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;
import java.util.UUID;

public class CustomerUtils {


    public static String generateCustomerNumber() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 1_000_000_000_000L);
        return String.format("CS%012d", number);
    }

    public static String generateTempPassword() {
        // Take first 8 characters from UUID
        return UUID.randomUUID().toString().substring(0, 8);
    }

}