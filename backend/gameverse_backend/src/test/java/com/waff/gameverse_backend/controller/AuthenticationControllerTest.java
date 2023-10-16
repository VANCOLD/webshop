package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.dto.LoginDto;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.Gender;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DirtiesContext
    void registerUserTest() throws Exception {

        // Should create a new user since the username isn't taken
        UserDto testCase1 = new UserDto();
        testCase1.setUsername("test");
        testCase1.setPassword("test");
        testCase1.setFirstname("test");
        testCase1.setLastname("test");
        testCase1.setGender(Gender.MALE.name());
        testCase1.setEmail("test@test.com");
        testCase1.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase1.setRole(new RoleDto(null, "test", List.of()));

        UserDto testCase2 = new UserDto();
        testCase2.setUsername("user");
        testCase2.setPassword("test");
        testCase2.setFirstname("test");
        testCase2.setLastname("test");
        testCase2.setGender(Gender.MALE.name());
        testCase2.setEmail("test@test.com");
        testCase2.setAddress(new AddressDto(null, "test","test","test","test"));
        testCase2.setRole(new RoleDto(null, "test", List.of()));

        // Testing if we can create a new user => should work
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", Matchers.is("test")));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());
    }

    @Test
    void loginUserTest() throws Exception {

        LoginDto testCase1 = new LoginDto("test","user");
        LoginDto testCase2 = new LoginDto("user","password");


        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase1))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict());


        mockMvc.perform(post("/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(testCase2))
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }
}
