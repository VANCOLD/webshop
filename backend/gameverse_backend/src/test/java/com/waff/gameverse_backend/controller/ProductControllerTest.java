package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.enums.EsrbRating;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableMethodSecurity // Needed to enable the PreAuthorize Tag in testing, will be ignored otherwise!
public class ProductControllerTest {
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
        mockMvc.perform(get("/api/products/all").header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", Matchers.is(7)));
    }


    @Test
    void findByIdTest() throws Exception {

        String token = this.getToken("admin");
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
    @DirtiesContext
    void saveTest() throws Exception {

        String token = this.getToken("admin");

        // New product, doesn"t exist in db
        ProductDto testCase1 = new ProductDto();
        testCase1.setName("Froggyo");
        testCase1.setDescription("froggo");
        testCase1.setPrice(120.00);
        testCase1.setImage("Cool frogs");
        testCase1.setTax(20);
        testCase1.setStock(2);
        testCase1.setCategory(new CategoryDto(1L, "Games"));
        testCase1.setProducer(new ProducerDto(1L, "Nintendo", new AddressDto(2L, "11-1 Hokotate-cho","601-8501","Kyoto","Japan")));
        testCase1.setConsoleGeneration(new ConsoleGenerationDto(3L, "Nintendo Switch" ));
        testCase1.setGtin("asdasd");

        // Already existing product, should return conflict!
        ProductDto testCase2 = new ProductDto();
        testCase2.setName("The Legend Of Zelda: Breath Of The Wild");
        testCase2.setDescription("Cool zelda");
        testCase2.setPrice(60.00);
        testCase2.setImage("Cool Image");
        testCase2.setTax(20);
        testCase2.setStock(200);
        testCase2.setCategory(new CategoryDto(1L, "Games"));
        testCase2.setProducer(new ProducerDto(1L, "Nintendo", new AddressDto(2L, "11-1 Hokotate-cho","601-8501","Kyoto","Japan")));
        testCase2.setConsoleGeneration(new ConsoleGenerationDto(3L, "Nintendo Switch" ));
        testCase2.setGtin("1234");

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

        String token = this.getToken("user");

        // New product, doesn"t exist in db
        ProductDto testCase = new ProductDto();
        testCase.setName("Froggyo");
        testCase.setDescription("froggo");
        testCase.setPrice(120.00);
        testCase.setImage("Cool frogs");
        testCase.setTax(20);
        testCase.setStock(2);
        testCase.setCategory(new CategoryDto(1L, "Games"));
        testCase.setProducer(new ProducerDto(1L, "Ninetndo", new AddressDto(2L, "11-1 Hokotate-cho","601-8501","Kyoto","Japan")));
        testCase.setGtin("asdasd");

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

        String token = this.getToken("admin");

        // Already existing product, should return conflict!
        ProductDto testCase1 = new ProductDto(
            1L, "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image",  20, 200, "1234",
            LocalDateTime.of(2022,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto(1L, "Nintendo Switch"), new CategoryDto(1L, "Games"),
                new ProducerDto(1L, "Nintendo",  new AddressDto(1L, "test", "test", "test", "test")),
            List.of(new GenreDto(1L, "Adventure")));

        ProductDto testCase2 = new ProductDto(
            1000L, "Froggyo","froggo", 120.00, "Cool frogs",  20, 2, "asdasd",
            LocalDateTime.of(2024,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto(2L, "Playstation 5"), new CategoryDto(1L, "Games"),
                new ProducerDto(2L, "Sony",  new AddressDto(1L, "test", "test", "test", "test")),
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

        String token = this.getToken("user");

        // Existing product in the db
        ProductDto testCase = new ProductDto(
            1L, "The Legend Of Zelda: Breath Of The Wild","Cool zelda", 60.00, "Cool Image",  20, 200, "1234",
            LocalDateTime.of(2022,1,1,12,0), EsrbRating.EVERYONE.getName(),
            new ConsoleGenerationDto(null, "Nintendo Switch"), new CategoryDto(null, "Games"), new ProducerDto(null, "Nitendo",  new AddressDto(null, "test", "test", "test", "test")),
            List.of(new GenreDto(null, "Adventure")));

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

        String token = this.getToken("admin");

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

        String token = this.getToken("user");

        // Already existing product in data.sql
        Long testCase = 1L;

        // Should return forbidden since the user doesn"t have to correct product
        mockMvc
            .perform(delete("/api/products/{id}",testCase)
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isForbidden());
    }
}