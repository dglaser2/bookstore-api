package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.domain.OrderItem;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OrderRepositoryImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void create_ShouldReturnOrderId_WhenOrderCreatedSuccessfully() {
//        Order order = new Order(1, 1, new Timestamp(new Date().getTime()), new BigDecimal("100.00"), Collections.singletonList(new OrderItem(1, 1, 1, 2)));
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//
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
//            keys.put("order_id", 1);
//            keyHolder.getKeyList().add(keys);
//
//            return null;
//        }).when(jdbcTemplate).update(any(PreparedStatementCreator.class), eq(keyHolder));
//
//        doAnswer(invocation -> 1).when(jdbcTemplate).update(anyString(), anyInt(), anyInt(), anyInt());
//
//        Integer orderId = orderRepository.create(order);
//        assertNotNull(orderId);
//        assertEquals(1, orderId);
//
//        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class), eq(keyHolder));
//        verify(jdbcTemplate, times(order.getOrderItems().size())).update(anyString(), anyInt(), anyInt(), anyInt());
//    }

    @Test
    void create_ShouldThrowBadRequestException_WhenOrderCreationFails() {
        Order order = new Order(1, 1, new Timestamp(new Date().getTime()), new BigDecimal("100.00"), Collections.singletonList(new OrderItem(1, 1, 1, 2)));

        doThrow(new RuntimeException()).when(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));

        assertThrows(BadRequestException.class, () -> orderRepository.create(order));
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenOrderExists() {
        Order order = new Order(1, 1, new Timestamp(new Date().getTime()), new BigDecimal("100.00"), null);
        List<OrderItem> orderItems = Arrays.asList(new OrderItem(1, 1, 1, 2), new OrderItem(2, 1, 2, 3));

        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(order);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(orderItems);

        Order foundOrder = orderRepository.getOrderById(1);
        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getOrderId());
        assertNotNull(foundOrder.getOrderItems());
        assertEquals(2, foundOrder.getOrderItems().size());
    }

    @Test
    void getOrderById_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new RuntimeException());

        assertThrows(ResourceNotFoundException.class, () -> orderRepository.getOrderById(1));
    }
}
