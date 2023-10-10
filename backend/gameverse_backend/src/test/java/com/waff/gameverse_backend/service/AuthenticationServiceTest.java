package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.Gender;
import com.waff.gameverse_backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
@SpringBootTest
@Import(AuthenticationService.class)
@ActiveProfiles("test")
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void registerUserTest() {

        UserDto testUser = new UserDto();
        testUser.setUsername("test");
        testUser.setPassword("test");
        testUser.setFirstname("test");
        testUser.setLastname("test");
        testUser.setGender(Gender.MALE.name());
        testUser.setEmail("test@test.com");
        testUser.setAddress(new AddressDto(null, "test","test","test"));
        testUser.setRole(new RoleDto(null, "test", List.of()));

        // Neuer User, sollte erstellt werden können da er noch nicht existiert!
        User user = authenticationService.registerUser(testUser);
        assertThat(user.getUsername()).isEqualTo(testUser.getUsername());

        // Diesen User haben wir vorher angelegt, sollte einen Fehler werfen da er schon existiert!
        assertThrows(BadCredentialsException.class, () -> authenticationService.registerUser(testUser));
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
