package com.example.leaderit.security;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class SecretKeyGenerator {

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateSecretKey() {
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(key);
    }
}