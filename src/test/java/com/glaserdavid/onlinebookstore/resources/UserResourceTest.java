package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserResource.class)
class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void loginUser_ShouldReturnToken() throws Exception {
        User user = new User(1, "testuser", "testuser@example.com", "password");
        when(userService.validateUser(any(String.class), any(String.class), any(String.class))).thenReturn(user);

        String userJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\" }";

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertTrue(jsonResponse.contains("token"));
                });
    }

    @Test
    void registerUser_ShouldReturnToken() throws Exception {
        User user = new User(1, "testuser", "testuser@example.com", "password");
        when(userService.registerUser(any(String.class), any(String.class), any(String.class))).thenReturn(user);

        String userJson = "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password\" }";

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String jsonResponse = result.getResponse().getContentAsString();
                    assertTrue(jsonResponse.contains("token"));
                });
    }
}
