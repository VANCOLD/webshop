package com.waff.gameverse_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.waff.gameverse_backend.datamodel.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtIssuer {

    private final JwtProperties jwtProperties;

    public String issue(String username, String password, List<Roles> roles) {
        return JWT.create()
                .withClaim("username",username)
                .withClaim("password",password)
                .withClaim("authorities",roles)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
