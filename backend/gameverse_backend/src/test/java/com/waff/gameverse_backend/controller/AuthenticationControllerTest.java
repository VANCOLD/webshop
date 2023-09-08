package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.RegistrationDto;
import com.waff.gameverse_backend.service.TokenService;
import com.waff.gameverse_backend.service.UserService;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void registerUserTest() throws Exception {

        RegistrationDto testCase1 = new RegistrationDto("test","user");
        RegistrationDto testCase2 = new RegistrationDto("user","password");

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
    void loginUserTest() {

    }
}
