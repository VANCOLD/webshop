package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.service.TokenService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableGlobalMethodSecurity(prePostEnabled = true) // Needed to enable the PreAuthorize Tag in testing, will be ignored otherwise!
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    String getToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin","password");

        // Testing if we can call all (should return a list with 3 elements)
        mockMvc.perform(get("/api/users/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(3)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/users/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin","password");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        // Testing if we get a user with a legit id for admin
        mockMvc.perform(get("/api/users/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username",Matchers.is("user")));

        mockMvc.perform(get("/api/users/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/users/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin","password");

        // New user, doesn't exist in db
        UserDto testCase1 = new UserDto("superpower", "password", new RoleDto("user"));

        // Already existing user, should return conflict!
        UserDto testCase2 = new UserDto("user", "password", new RoleDto("user"));

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

        String token = this.getToken("user","password");

        // New user, doesn't exist in db
        RoleDto testCase = new RoleDto("superpower");

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

        String token = this.getToken("admin","password");


        // Already existing user, should be ok
        UserDto testCase1 = new UserDto(1L, "test", "password");

        // New user, doesn't exist in db => NotFound
        UserDto testCase2 = new UserDto(1000L, "superpower", "password");

        // user id exists but name is empty => Conflict
        UserDto testCase3 = new UserDto(1L, "", "password");

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

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/users")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase3))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }

    @Test
    void updateNoPrivilegeTest() throws Exception {

        String token = this.getToken("user","password");

        // Existing user in the db
        RoleDto testCase = new RoleDto( 1L, "superpower");

        // Should return forbidden since the user doesn't have to correct user
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

        String token = this.getToken("admin","password");

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

        String token = this.getToken("user","password");

        // Already existing user in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn't have to correct user
        mockMvc
            .perform(delete("/api/users/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

}