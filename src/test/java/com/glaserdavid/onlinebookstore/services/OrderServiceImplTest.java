package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenOrderExists() {
        Order order = new Order(1, 1, null, null, null); // Add appropriate parameters
        when(orderRepository.getOrderById(anyInt())).thenReturn(order);

        Order foundOrder = orderService.getOrderById(1);

        assertNotNull(foundOrder);
        assertEquals(1, foundOrder.getOrderId());
    }

    @Test
    void getOrderById_ShouldThrowResourceNotFoundException_WhenOrderDoesNotExist() {
        when(orderRepository.getOrderById(anyInt())).thenThrow(new ResourceNotFoundException("Order not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(1);
        });
    }

    @Test
    void createOrder_ShouldReturnOrder_WhenOrderIsValid() {
        Order order = new Order(1, 1, null, null, null); // Add appropriate parameters
        when(orderRepository.create(any(Order.class))).thenReturn(1);
        when(orderRepository.getOrderById(anyInt())).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(1, createdOrder.getOrderId());
    }

    @Test
    void createOrder_ShouldThrowBadRequestException_WhenOrderIsInvalid() {
        Order order = new Order(1, 1, null, null, null); // Add appropriate parameters
        when(orderRepository.create(any(Order.class))).thenThrow(new BadRequestException("Invalid order details"));

        assertThrows(BadRequestException.class, () -> {
            orderService.createOrder(order);
        });
    }

    @Test
    void createOrder_ShouldThrowResourceNotFoundException_WhenOrderCreationFails() {
        Order order = new Order(1, 1, null, null, null); // Add appropriate parameters
        when(orderRepository.create(any(Order.class))).thenReturn(1);
        when(orderRepository.getOrderById(anyInt())).thenThrow(new ResourceNotFoundException("Order not found"));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.createOrder(order);
        });
    }
}
