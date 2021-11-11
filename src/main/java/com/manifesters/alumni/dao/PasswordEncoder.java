package com.manifesters.alumni.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

    public static void main(String args[]) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "test@123";
        String encodedPassword =encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
