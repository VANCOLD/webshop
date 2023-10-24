package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.RoleRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The AuthenticationService class provides methods for user authentication and registration.
 * It interacts with the UserRepository, RoleRepository, and TokenService to manage users and authentication tokens.
 */
@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * Constructs a new AuthenticationService instance.
     *
     * @param userRepository         The UserRepository for managing user data.
     * @param roleRepository         The RoleRepository for managing user roles.
     * @param passwordEncoder        The PasswordEncoder for encoding user passwords.
     * @param authenticationManager  The AuthenticationManager for user authentication.
     * @param tokenService           The TokenService for generating authentication tokens.
     */
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                 TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Register a new user with the given username and password.
     *
     * @param userDto userDto that contains all the user data for registration
     * @return The registered user.
     * @throws BadCredentialsException If a user with the same username already exists.
     */
    public User registerUser(UserDto userDto) {
        Optional<User> checkUser = userRepository.findByUsername(userDto.getUsername());
        if (checkUser.isPresent()) {
            throw new BadCredentialsException("User with the given name already exists!");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        Role userRole = roleRepository.findByName("user").orElseThrow(); // Assumes "user" role exists.
        userDto.setRole(userRole.convertToDto());
        userDto.setPassword(encodedPassword);
        return userRepository.save(new User(userDto));
    }

    /**
     * Authenticate a user with the given username and password and generate an authentication token.
     *
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return The generated authentication token.
     * @throws AuthenticationException If authentication fails.
     */
    public String loginUser(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
            return tokenService.generateJwt(authentication);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
