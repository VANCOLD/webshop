package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.LoginDto;
import com.waff.gameverse_backend.dto.SimpleUserDto;
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
 * The AuthenticationController class handles user registration and authentication.
 */
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Constructs a new AuthenticationController with the provided AuthenticationService.
     *
     * @param authenticationService The AuthenticationService to use for user registration and authentication.
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Registers a new user with the provided registration information.
     *
     * @param userDto The UserDto containing user registration information.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the registered user's information.
     * @throws ResponseStatusException with HTTP status 409 (CONFLICT) if a user with the same username already exists.
     */
    @PostMapping("/register")
    public ResponseEntity<SimpleUserDto> registerUser(@RequestBody UserDto userDto) {
        try {
            var test = this.authenticationService.registerUser(userDto);
            System.out.println(test.convertToDto().toString());
            return ResponseEntity.ok(test.convertToSimpleDto());
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists!", ex);
        }
    }

    /**
     * Authenticates a user with the provided username and password.
     *
     * @param loginDto The RegistrationDto containing user authentication information.
     * @return ResponseEntity<String> A ResponseEntity containing an authentication token.
     * @throws ResponseStatusException with HTTP status 409 (CONFLICT) if the user doesn't exist or authentication fails.
     */
    @PostMapping("/authenticate")
    public String loginUser(@RequestBody LoginDto loginDto) {
        try {
            return authenticationService.loginUser(loginDto.getUsername(), loginDto.getPassword());
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User doesn't exists!", ex);
        }
    }
}
