package com.waff.gameverse_backend.Utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Setter
@Getter
@Component
public class RsaKeyProperties {

  private RSAPublicKey rsaPublicKey;

  private RSAPrivateKey rsaPrivateKey;

  public RsaKeyProperties() {
    KeyPair keyPair = KeyGenerator.generateRsaKey();
    this.rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
    this.rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
  }
}
