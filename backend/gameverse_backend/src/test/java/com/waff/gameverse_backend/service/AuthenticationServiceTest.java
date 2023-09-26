package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@DirtiesContext
@Import(AuthenticationService.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void registerUserTest() {

        String username = "test";
        String password = "user";

        // Neuer User, sollte erstellt werden können da er noch nicht existiert!
        User user = authenticationService.registerUser(username,password);
        assertThat(user.getUsername()).isEqualTo(username);

        // Diesen User haben wir vorher angelegt, sollte einen Fehler werfen da er schon existiert!
        assertThrows(BadCredentialsException.class, () -> authenticationService.registerUser(username,password));
    }

    @Test
    void loginUserTest() {

        String username = "user";
        String password = "password";

        String postfix  = "abcdefg";

        // Wenn wir einen String zurückbekommen haben es geklappt, ansonsten würden wir eie Exception bekommen!
        assertThat(authenticationService.loginUser(username,password)).isNotEmpty();

        assertThrows(BadCredentialsException.class, () -> authenticationService.loginUser(username + postfix,password));
        assertThrows(BadCredentialsException.class, () -> authenticationService.loginUser(username, password + postfix));
    }
}
