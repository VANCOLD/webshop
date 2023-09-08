package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.RegistrationDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * AuthenticationController, welcher JWT Tokens nutzt.
 * Es gibt 2 Routen, register & authenticate
 * register erstellt einen neuen Nutzer (falls der username nicht vergeben ist)
 * und authenticate generiert einen JWT Token.
 * Beide Routes sind freizugänglich
 */
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegistrationDto registrationDto) {
        try {
            return ResponseEntity.ok(this.authenticationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword()).convertToDto());
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists!", ex);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> loginUser(@RequestBody RegistrationDto registrationDto) {

        try {
            return ResponseEntity.ok(authenticationService.loginUser(registrationDto.getUsername(), registrationDto.getPassword()));

        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exists!", ex);
        }
    }

}
