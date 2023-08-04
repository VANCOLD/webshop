package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.LoginResponseDto;
import com.waff.gameverse_backend.dto.RegistrationDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController, welcher JWT Tokens nutzt.
 * Es gibt 2 Routen, register & authenticate
 * register erstellt einen neuen Nutzer (falls der username nicht vergeben ist)
 * und authenticate generiert einen JWT Token.
 * Beide Routes sind freizugänglich
 */
@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping("/register")
  public UserDto registerUser(
    @RequestBody RegistrationDto registrationDto) {
    return this.authenticationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword()).convertToDto();
  }


  @PostMapping("/authenticate")
  public LoginResponseDto loginUser(
    @RequestBody RegistrationDto registrationDto) {
    return authenticationService.loginUser(registrationDto.getUsername(), registrationDto.getPassword());
  }


}
