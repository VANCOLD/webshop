package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.enums.EsrbRating;
import com.waff.gameverse_backend.service.ProductService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
@EnableGlobalMethodSecurity(prePostEnabled = true) // Needed to enable the PreAuthorize Tag in testing, will be ignored otherwise!
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProductService productService;

    String getToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenService.generateJwt(authentication);
    }

    @Test
    void findAllTest() throws Exception {

        String token = this.getToken("admin","password");

        // Testing if we can call all (should return a list with 3 elements)
        mockMvc.perform(get("/api/products/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(7)));
    }

    @Test
    void findAllNoPrivilegeTest() throws Exception {

        // User with no privileges shouldn"t be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/products/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin","password");
        Long testCase1    = 1L;
        Long testCase2    = 1000L;

        String productName  = "The Legend Of Zelda: Breath Of The Wild";

        // Testing if we get a product with a legit id for admin
        mockMvc.perform(get("/api/products/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name",Matchers.is(productName)));

        mockMvc.perform(get("/api/products/{id}",testCase2).header("Authorization", "Bearer " + token))
            .andExpect(status().isNoContent());
    }

    @Test
    void findByIdNoPrivilegeTest() throws Exception {

        Long testCase1    = 1L;
        // User with no privileges shouldn"t be able to call the route
        String token = this.getToken("user","password");

        mockMvc.perform(get("/api/products/{id}",testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin","password");

        // New product, doesn"t exist in db
        SimpleProductDto testCase1 = new SimpleProductDto(
        "Froggyo","froggo", 120.00, "Cool frogs", (byte) 20, 2, "asdasd");

        // Already existing product, should return conflict!
        SimpleProductDto testCase2 = new SimpleProductDto(
            "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image", (byte) 20, 200, "1234");

        // Should be ok and return the newly created product
        mockMvc
            .perform(post("/api/products")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())));

        // Should return conflict because the id doesn"t exist
        mockMvc
            .perform(post("/api/products")
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

        // New product, doesn"t exist in db
        SimpleProductDto testCase = new SimpleProductDto(
            "Froggyo","froggo", 120.00, "Cool frogs", (byte) 20, 2, "asdasd");

        // Should return forbidden since the user doesn"t have to correct product
        mockMvc
            .perform(post("/api/products")
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

        // Already existing product, should return conflict!
        ProductDto testCase1 = new ProductDto(
            1L, "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image", (byte) 20, 200, "1234",
            LocalDateTime.of(2022,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto("Nintendo Switch"), new CategoryDto("Games"), new ProducerDto("Nitendo"),
            List.of(new GenreDto("Adventure")));

        ProductDto testCase2 = new ProductDto(
            1000L, "Froggyo","froggo", 120.00, "Cool frogs", (byte) 20, 2, "asdasd",
            LocalDateTime.of(2024,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto(2L, "Playstation 5"), new CategoryDto(1L, "Games"), new ProducerDto(2L, "Sony"),
            List.of(new GenreDto(9L, "Survival & Horror")));


        // Should be ok and return the updated product
        mockMvc
            .perform(put("/api/products")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(testCase1.getName())));

        // Should be not found because the id is invalid!
        mockMvc
            .perform(put("/api/products")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    }

    @Test
    void updateNoPrivilegeTest() throws Exception {

        String token = this.getToken("user","password");

        // Existing product in the db
        ProductDto testCase = new ProductDto(
            1L, "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image", (byte) 20, 200, "1234",
            LocalDateTime.of(2022,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto("Nintendo Switch"), new CategoryDto("Games"), new ProducerDto("Nitendo"),
            List.of(new GenreDto("Adventure")));

        // Should return forbidden since the user doesn"t have to correct privilege
        mockMvc
            .perform(put("/api/products")
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

        // First product in data.sql
        String productName = "The Legend Of Zelda: Breath Of The Wild";

        // product exist, should delete without a problem
        Long testCase1 = 1L;

        // bogus ids don"t work either => NotFound
        Long testCase3 = 1000L;

        // Should be ok and return the updated product
        mockMvc
            .perform(delete("/api/products/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", Matchers.is(productName)));

        // Should be not found because the product was deleted in the last mock call!
        mockMvc
            .perform(delete("/api/products/{id}", testCase1).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

        // Should be not found because the id is invalid!
        mockMvc
            .perform(delete("/api/products/{id}", testCase3).header("Authorization", "Bearer " + token))
            .andExpect(status().isNotFound());

    }

    @Test
    void deleteNoPrivilegeTest() throws Exception {

        String token = this.getToken("user","password");

        // Already existing product in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn"t have to correct product
        mockMvc
            .perform(delete("/api/products/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }
}