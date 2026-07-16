package com.util;

import java.security.SecureRandom;
import java.util.Random;

public class CustomerUtils {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    private static final int PASSWORD_LENGTH = 12;

    public static String generateCustomerNumber() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 1_000_000_000_000L);
        return String.format("CS%012d", number);
    }

    public static String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }
}