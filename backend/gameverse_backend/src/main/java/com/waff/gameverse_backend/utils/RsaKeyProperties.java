package com.waff.gameverse_backend.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * The RsaKeyProperties class is a Spring component responsible for managing RSA public and private keys.
 * It initializes these keys upon construction using the KeyGenerator utility.
 */
@Setter
@Getter
@Component
public class RsaKeyProperties {

    private RSAPublicKey rsaPublicKey;

    private RSAPrivateKey rsaPrivateKey;

    /**
     * Constructs a new RsaKeyProperties instance and generates RSA key pair upon initialization.
     */
    public RsaKeyProperties() {
        KeyPair keyPair = KeyGenerator.generateRsaKey();
        this.rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        this.rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
    }
}
