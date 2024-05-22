package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.exceptions.AuthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void create_ShouldReturnUserId_WhenUserCreatedSuccessfully() {
//        String username = "testuser567";
//        String email = "anne@example.com";
//        String password = "password";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        doAnswer(invocation -> {
//            PreparedStatementCreator psc = invocation.getArgument(0);
//            Connection connection = mock(Connection.class);
//            PreparedStatement ps = mock(PreparedStatement.class);
//            when(ps.executeUpdate()).thenReturn(1);
//            when(connection.prepareStatement(anyString(), anyInt())).thenReturn(ps);
//            psc.createPreparedStatement(connection);
//
//            // Simulate key generation
//            Map<String, Object> keys = new HashMap<>();
//            keys.put("user_id", 15);
//            keyHolder.getKeyList().add(keys);
//
//            return null;
//        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), eq(keyHolder));
//
//        Integer userId = userRepository.create(username, email, password);
//        assertNotNull(userId);
//        assertEquals(1, userId);
//    }

    @Test
    void create_ShouldThrowAuthException_WhenUserCreationFails() {
        String username = "testuser";
        String email = "testuser@example.com";
        String password = "password";

        doThrow(new RuntimeException()).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        assertThrows(AuthException.class, () -> userRepository.create(username, email, password));
    }

    @Test
    void findByEmailAndPassword_ShouldReturnUser_WhenCredentialsAreValid() {
        String email = "testuser@example.com";
        String password = "password";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        User user = new User(1, "testuser", email, hashedPassword);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(user);

        User foundUser = userRepository.findByEmailAndPassword(email, password);
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
    }

    @Test
    void findByEmailAndPassword_ShouldThrowAuthException_WhenPasswordIsInvalid() {
        String email = "testuser@example.com";
        String password = "wrongpassword";
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt(10));
        User user = new User(1, "testuser", email, hashedPassword);

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(user);

        assertThrows(AuthException.class, () -> userRepository.findByEmailAndPassword(email, password));
    }

    @Test
    void findByEmailAndPassword_ShouldThrowAuthException_WhenEmailIsNotFound() {
        String email = "testuser@example.com";
        String password = "password";

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(AuthException.class, () -> userRepository.findByEmailAndPassword(email, password));
    }

    @Test
    void getCountByEmail_ShouldReturnCount_WhenEmailExists() {
        String email = "testuser@example.com";
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), eq(Integer.class)))
                .thenReturn(1);

        Integer count = userRepository.getCountByEmail(email);
        assertNotNull(count);
        assertEquals(1, count.intValue());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = new User(1, "testuser", "testuser@example.com", "password");
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(user);

        User foundUser = userRepository.findById(1);
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void findById_ShouldThrowAuthException_WhenUserDoesNotExist() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EmptyResultDataAccessException.class, () -> userRepository.findById(1));
    }
}
