package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.ProducerDto;
import com.waff.gameverse_backend.service.ProducerService;
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
public class ProducerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProducerService producerService;

    String getToken(String username) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, "password"));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin");

        // Testing if we can call all (should return a list with 3 elements)
        mockMvc.perform(get("/api/producers/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(10)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/producers/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        String producerName  = "Nintendo";

        // Testing if we get a producer with a legit id for admin
        mockMvc.perform(get("/api/producers/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name",Matchers.is(producerName)));

        mockMvc.perform(get("/api/producers/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/producers/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin");

        // New producer, doesn't exist in db
        ProducerDto testCase1 = new ProducerDto("Froggyo");

        // Already existing producer, should return conflict!
        ProducerDto testCase2 = new ProducerDto("Nintendo");

        // Current amount of producers, will be used to see if the new cat has count + 1 as id
        int currentCount = producerService.findAll().size();

        // Should be ok and return the newly created producer
        mockMvc
            .perform(post("/api/producers")
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
            .perform(post("/api/producers")
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

        // New producer, doesn't exist in db
        ProducerDto testCase = new ProducerDto("Froggyo");

        // Should return forbidden since the user doesn't have to correct producer
        mockMvc
            .perform(post("/api/producers")
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

        // should work
        ProducerDto testCase1 = new ProducerDto(1L,  "Nintendo");

        // New producer, doesn't exist in db => NotFound
        ProducerDto testCase2 = new ProducerDto(1000L,  "Froggyo");

        // Should be ok and return the updated producer
        mockMvc
            .perform(put("/api/producers")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())));

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/producers")
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

        // Existing producer in the db
        ProducerDto testCase = new ProducerDto(1L,  "Nintendo");

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(put("/api/producers")
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

        // First producer in data.sql
        String producerName = "Nintendo";

        // producer exist, should delete without a problem
        Long testCase1 = 1L;


        // bogus ids don't work either => NotFound
        Long testCase3 = 1000L;

        // Should be ok and return the updated producer
        mockMvc
            .perform(delete("/api/producers/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(producerName)));

        // Should be not found because the producer was deleted in the last mock call!
        mockMvc
            .perform(delete("/api/producers/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(delete("/api/producers/{id}", testCase3).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

    }

    @Test
    void deleteNoPrivilegeTest() throws Exception {

        String token = this.getToken("user");

        // Already existing producer in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn't have to correct producer
        mockMvc
            .perform(delete("/api/producers/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }
}