package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.exceptions.AuthException;
import com.glaserdavid.onlinebookstore.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validateUser_ShouldReturnUser_WhenCredentialsAreValid() throws AuthException {
        User user = new User(1, "testuser", "test@example.com", "password");
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenReturn(user);

        User validatedUser = userService.validateUser("testuser", "test@example.com", "password");

        assertNotNull(validatedUser);
        assertEquals("testuser", validatedUser.getUsername());
        assertEquals("test@example.com", validatedUser.getEmail());
    }

    @Test
    void validateUser_ShouldThrowAuthException_WhenEmailIsNull() {
        assertThrows(AuthException.class, () -> {
            userService.validateUser("testuser", null, "password");
        });
    }

    @Test
    void validateUser_ShouldThrowAuthException_WhenCredentialsAreInvalid() {
        when(userRepository.findByEmailAndPassword(anyString(), anyString())).thenThrow(new AuthException("Invalid credentials"));

        assertThrows(AuthException.class, () -> {
            userService.validateUser("testuser", "test@example.com", "wrongpassword");
        });
    }

    @Test
    void registerUser_ShouldReturnUser_WhenRegistrationIsValid() throws AuthException {
        User user = new User(1, "testuser", "test@example.com", "password");
        when(userRepository.getCountByEmail(anyString())).thenReturn(0);
        when(userRepository.create(anyString(), anyString(), anyString())).thenReturn(1);
        when(userRepository.findById(anyInt())).thenReturn(user);

        User registeredUser = userService.registerUser("testuser", "test@example.com", "password");

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        assertEquals("test@example.com", registeredUser.getEmail());
    }

    @Test
    void registerUser_ShouldThrowAuthException_WhenEmailIsInvalid() {
        assertThrows(AuthException.class, () -> {
            userService.registerUser("testuser", "invalidemail", "password");
        });
    }

    @Test
    void registerUser_ShouldThrowAuthException_WhenEmailAlreadyInUse() {
        when(userRepository.getCountByEmail(anyString())).thenReturn(1);

        assertThrows(AuthException.class, () -> {
            userService.registerUser("testuser", "test@example.com", "password");
        });
    }
}
