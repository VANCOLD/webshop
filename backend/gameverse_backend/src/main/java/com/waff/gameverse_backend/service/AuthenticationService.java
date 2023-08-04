package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.LoginResponseDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.RoleRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  public User registerUser(String username, String password) {

    String encodedPassword = passwordEncoder.encode(password);
    Role userRole = roleRepository.findByName("user").get(); // We know this exists, because it is created on boot in through the data.sql

    return userRepository.save(new User(username, encodedPassword, userRole));
  }

  public LoginResponseDto loginUser(String username, String password) {

    try {

      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      String token = tokenService.generateJwt(authentication);

      return new LoginResponseDto(userRepository.findByUsername(username).get().convertToDto(), token);

    } catch (AuthenticationException ex) {
      return new LoginResponseDto(null, "");
    }
  }
}
