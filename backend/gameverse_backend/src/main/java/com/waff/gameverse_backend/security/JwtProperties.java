package com.waff.gameverse_backend.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter @Getter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {

    /**
     * Secret Key used for generating jwt tokens; Do not share it!
     */
    private String secretKey;
}
