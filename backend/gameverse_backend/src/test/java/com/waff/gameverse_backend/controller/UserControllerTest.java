package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.Gender;
import com.waff.gameverse_backend.service.TokenService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableMethodSecurity // Needed to enable the PreAuthorize Tag in testing, will be ignored otherwise!
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    String getToken(String username) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, "password"));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin");

        // Testing if we can call all (should return a list with 3 elements)
        mockMvc.perform(get("/api/users/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(2)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/users/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        String username   = "user";

        // Testing if we get a user with a legit id for admin
        mockMvc.perform(get("/api/users/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username",Matchers.is(username)));

        mockMvc.perform(get("/api/users/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/users/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin");

        // New user, doesn't exist in db
        UserDto testCase1 = new UserDto();
        testCase1.setUsername("test");
        testCase1.setPassword("test");
        testCase1.setFirstname("test");
        testCase1.setLastname("test");
        testCase1.setGender(Gender.MALE.name());
        testCase1.setEmail("test@test.com");
        testCase1.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase1.setRole(new RoleDto(null, "test", List.of()));

        // Already existing user, should return conflict!
        UserDto testCase2 = new UserDto();
        testCase2.setUsername("user");
        testCase2.setPassword("test");
        testCase2.setFirstname("test");
        testCase2.setLastname("test");
        testCase2.setGender(Gender.MALE.name());
        testCase2.setEmail("test@test.com");
        testCase2.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase2.setRole(new RoleDto(null, "test", List.of()));

        // Should be ok and return the newly created user
        mockMvc
            .perform(post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", Matchers.is(testCase1.getUsername())));

        // Should return conflict because the id doesn't exist
        mockMvc
            .perform(post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }

    @Test
    void saveNoPrivilegeTest() throws Exception {

        String token = this.getToken("user");

        // New user, doesn't exist in db
        UserDto testCase = new UserDto();
        testCase.setUsername("test");
        testCase.setPassword("test");
        testCase.setFirstname("test");
        testCase.setLastname("test");
        testCase.setGender(Gender.MALE.name());
        testCase.setEmail("test@test.com");
        testCase.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase.setRole(new RoleDto(null, "test", List.of()));

        // Should return forbidden since the user doesn't have to correct user
        mockMvc
            .perform(post("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }


    @Test
    @DirtiesContext
    void updateTest() throws Exception {

        String token = this.getToken("admin");

        // Already existing user, should be ok
        UserDto testCase1 = new UserDto();
        testCase1.setId(1L);
        testCase1.setUsername("test");
        testCase1.setPassword("test");
        testCase1.setFirstname("test");
        testCase1.setLastname("test");
        testCase1.setGender(Gender.MALE.name());
        testCase1.setEmail("test@test.com");
        testCase1.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase1.setRole(new RoleDto(null, "test", List.of()));

        // New user, doesn't exist in db => NotFound
        UserDto testCase2 = new UserDto();
        testCase2.setId(1000L);
        testCase2.setUsername("test");
        testCase2.setPassword("test");
        testCase2.setFirstname("test");
        testCase2.setLastname("test");
        testCase2.setGender(Gender.MALE.name());
        testCase2.setEmail("test@test.com");
        testCase2.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase2.setRole(new RoleDto(null, "test", List.of()));

        // Should be ok and return the updated user
        mockMvc
            .perform(put("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", Matchers.is(testCase1.getUsername())));

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    }

    @Test
    void updateNoPrivilegeTest() throws Exception {

        String token = this.getToken("user");

        // Existing user in the db
        UserDto testCase = new UserDto();
        testCase.setId(1L);
        testCase.setUsername("test");
        testCase.setPassword("test");
        testCase.setFirstname("test");
        testCase.setLastname("test");
        testCase.setGender(Gender.MALE.name());
        testCase.setEmail("test@test.com");
        testCase.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase.setRole(new RoleDto(null, "test", List.of()));

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(put("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }


    @Test
    @DirtiesContext
    void deleteTest() throws Exception {

        String token = this.getToken("admin");

        // First user in data.sql
        String privName = "user";

        // user exist, should delete without a problem
        Long testCase1 = 1L;

        // Should be deleted, name doesn't matter, deletions work with ids => NotFound
        Long testCase2 = 1L;

        // bogus ids don't work either => NotFound
        Long testCase3 = 1000L;

        // Should be ok and return the updated user
        mockMvc
            .perform(delete("/api/users/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", Matchers.is(privName)));

        // Should be not found because the user was deleted in the last mock call!
        mockMvc
            .perform(delete("/api/users/{id}", testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(delete("/api/users/{id}", testCase3).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

    }

    @Test
    void deleteNoPrivilegeTest() throws Exception {

        String token = this.getToken("user");

        // Already existing user in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn't have to correct user
        mockMvc
            .perform(delete("/api/users/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }
}