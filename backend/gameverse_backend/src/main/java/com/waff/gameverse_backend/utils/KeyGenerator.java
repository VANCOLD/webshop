package com.waff.gameverse_backend.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * The KeyGenerator class provides a utility method for generating an RSA KeyPair.
 * It uses the RSA algorithm and generates a 2048-bit key pair.
 */
public class KeyGenerator {

    /**
     * Generate an RSA KeyPair with a 2048-bit key length.
     *
     * @return The generated RSA KeyPair.
     * @throws IllegalStateException If an error occurs during key generation.
     */
    public static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException("Error generating RSA key pair", e);
        }
        return keyPair;
    }
}
