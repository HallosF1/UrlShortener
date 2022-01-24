package com.hallos.urlshortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RandomStringGenerator {

    @Value("${codeLength}")
    private int codeLength;

    public String generateRandomString() {

        SecureRandom random = new SecureRandom();

        String generatedCode = "";

        var keys = "abcdefghijklmnopqrstuvwxyz0123456789"
                .toUpperCase()
                .chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.toList());

        Collections.shuffle(keys);

        for (int i = 0; i < codeLength; i++) {
            generatedCode += random.nextInt(keys.size());
        }

        return generatedCode;
    }
}
