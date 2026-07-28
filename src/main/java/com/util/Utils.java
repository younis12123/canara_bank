package com.util;

import com.enums.LoanType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Random;
import java.util.UUID;

public class Utils {


    public static String generateCustomerNumber() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 1_000_000_000_000L);
        return String.format("CS%012d", number);
    }

    public static String generateTempPassword() {
        // Take first 8 characters from UUID
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateAccountNumber() {
        Random random = new Random();
        long number = 100000000000L + (long)(random.nextDouble() * 900000000000L);
        return String.valueOf(number);
    }
    public static String generateEmployeeNumber() {
        Random random = new Random();
        long number = (long) (random.nextDouble() * 1_000_000_000_000L);
        return String.format("EMP%012d", number);
    }

    public static double determineInterestRate(LoanType loanType) {
        return switch (loanType) {
            case PERSONAL -> 12.0;
            case HOME     -> 8.5;
            case CAR      -> 9.0;
            case EDUCATION-> 7.5;
            default       -> 10.0;
        };
    }

    public static double calculateEmi(double ammount, double annualRate, int months) {
        double monthlyRate = annualRate / 12 / 100;
        return (ammount * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                (Math.pow(1 + monthlyRate, months) - 1);
    }

    public static String generateTransactionRef() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

}