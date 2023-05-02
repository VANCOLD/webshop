package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.security.JwtIssuer;
import com.waff.gameverse_backend.datamodel.LoginResponse;
import com.waff.gameverse_backend.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtIssuer jwtIssuer;

    @PostMapping
    public LoginResponse login(@RequestBody @Validated LoginDTO loginDTO) {

        var token = jwtIssuer.issue(loginDTO.getUsername(), loginDTO.getPassword(), List.of());

        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }
}
