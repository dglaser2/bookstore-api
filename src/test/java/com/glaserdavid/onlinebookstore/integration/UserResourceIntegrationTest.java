package com.glaserdavid.onlinebookstore.integration;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

//    @Test
//    @WithMockUser
//    void registerUser_ShouldCreateUserAndReturnToken() throws Exception {
//        String userJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\" }";
//
//        mockMvc.perform(post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token", notNullValue()));
//
//        User user = userRepository.findByEmailAndPassword("testuser@example.com", "password");
//        assertNotNull(user);
//        assertEquals("testuser", user.getUsername());
//    }

//    @Test
//    @WithMockUser
//    void loginUser_ShouldReturnToken() throws Exception {
//        String username = "testuser";
//        String email = "testuser@example.com";
//        String password = "password";
//
//        // Creating user before testing login
//        userRepository.create(username, email, password);
//
//        String userJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\" }";
//
//        mockMvc.perform(post("/api/users/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token", notNullValue()));
//    }
}
