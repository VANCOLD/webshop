package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.service.PrivilegeService;
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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableGlobalMethodSecurity(prePostEnabled = true) // Needed to enable the PreAuthorize Tag in testing, will be ignored otherwise!
public class PrivilegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PrivilegeService privilegeService;

    String getToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin","password");

        // Testing if we can call all (should return a list with 8 elements)
        mockMvc.perform(get("/api/privileges/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(8)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/privileges/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin","password");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        // Testing if we get a privilege with a legit id for admin
        mockMvc.perform(get("/api/privileges/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name",Matchers.is("view_profile")));

        mockMvc.perform(get("/api/privileges/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/privileges/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin","password");

        // New privilege, doesn't exist in db
        PrivilegeDto testCase1 = new PrivilegeDto("superpower");

        // Already existing privilege, should return conflict!
        PrivilegeDto testCase2 = new PrivilegeDto("edit_users");

        // Current amount of privileges, will be used to see if the new priv has count + 1 as id
        int currentCount = privilegeService.findAll().size();

        // Should be ok and return the newly created privilege
        mockMvc
            .perform(post("/api/privileges")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())))
            .andExpect(jsonPath("$.id",Matchers.is(currentCount + 1)));

        // Should return conflict because the id doesn't exist
        mockMvc
            .perform(post("/api/privileges")
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

        // New privilege, doesn't exist in db
        PrivilegeDto testCase = new PrivilegeDto("superpower");

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(post("/api/privileges")
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


        // Already existing privilege, should return conflict!
        PrivilegeDto testCase1 = new PrivilegeDto(1L, "view_profiles");

        // New privilege, doesn't exist in db => NotFound
        PrivilegeDto testCase2 = new PrivilegeDto(1000L, "superpower");

        // Privilege id exists but name is empty => Conflict
        PrivilegeDto testCase3 = new PrivilegeDto(1L, "");

        // Should be ok and return the updated privilege
        mockMvc
            .perform(put("/api/privileges")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())));

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/privileges")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/privileges")
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

        // Existing privilege in the db
        PrivilegeDto testCase = new PrivilegeDto(1L, "superpower");

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(put("/api/privileges")
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

        // First privilege in data.sql
        String privName = "view_profile";
        // Privilege exist, should delete without a problem
        Long testCase1 = 1L;

        // Should be deleted, name doesn't matter, deletions work with ids => NotFound
        Long testCase2 = 1L;

        // bogus ids don't work either => NotFound
        Long testCase3 = 1000L;

        // Should be ok and return the updated privilege
        mockMvc
            .perform(delete("/api/privileges/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(privName)));

        // Should be not found because the privilege was deleted in the last mock call!
        mockMvc
            .perform(delete("/api/privileges/{id}", testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(delete("/api/privileges/{id}", testCase3).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

    }

    @Test
    void deleteNoPrivilegeTest() throws Exception {

        String token = this.getToken("user","password");

        // Already existing privilege in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(delete("/api/privileges/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

}
