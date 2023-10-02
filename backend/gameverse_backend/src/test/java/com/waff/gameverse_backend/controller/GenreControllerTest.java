package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.GenreDto;
import com.waff.gameverse_backend.service.GenreService;
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
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private GenreService genreService;

    String getToken(String username) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, "password"));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin");

        // Testing if we can call all (should return a list with 3 elements)
        mockMvc.perform(get("/api/genres/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(10)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/genres/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        String genreName  = "First Person Shooters";

        // Testing if we get a genre with a legit id for admin
        mockMvc.perform(get("/api/genres/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name",Matchers.is(genreName)));

        mockMvc.perform(get("/api/genres/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn't be able to call the route
        String token = this.getToken("user");

        mockMvc.perform(get("/api/genres/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin");

        // New genre, doesn't exist in db
        GenreDto testCase1 = new GenreDto("MOBA");

        // Already existing genre, should return conflict!
        GenreDto testCase2 = new GenreDto("First Person Shooters");

        // Current amount of genres, will be used to see if the new cat has count + 1 as id
        int currentCount = genreService.findAll().size();

        // Should be ok and return the newly created genre
        mockMvc
            .perform(post("/api/genres")
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
            .perform(post("/api/genres")
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

        // New genre, doesn't exist in db
        GenreDto testCase = new GenreDto("MOBA");

        // Should return forbidden since the user doesn't have to correct genre
        mockMvc
            .perform(post("/api/genres")
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
        GenreDto testCase1 = new GenreDto(1L,  "First Person Shooters");

        // New genre, doesn't exist in db => NotFound
        GenreDto testCase2 = new GenreDto(1000L,  "MOBA");

        // Should be ok and return the updated genre
        mockMvc
            .perform(put("/api/genres")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())));

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/genres")
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

        // Existing genre in the db
        GenreDto testCase = new GenreDto( "First Person Shooters");

        // Should return forbidden since the user doesn't have to correct privilege
        mockMvc
            .perform(put("/api/genres")
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

        // First genre in data.sql
        String genreName = "First Person Shooters";

        // genre exist, should delete without a problem
        Long testCase1 = 1L;


        // bogus ids don't work either => NotFound
        Long testCase3 = 1000L;

        // Should be ok and return the updated genre
        mockMvc
            .perform(delete("/api/genres/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(genreName)));

        // Should be not found because the genre was deleted in the last mock call!
        mockMvc
            .perform(delete("/api/genres/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(delete("/api/genres/{id}", testCase3).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

    }

    @Test
    void deleteNoPrivilegeTest() throws Exception {

        String token = this.getToken("user");

        // Already existing genre in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn't have to correct genre
        mockMvc
            .perform(delete("/api/genres/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }
}