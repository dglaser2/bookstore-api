package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.domain.OrderItem;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;

import java.util.List;

public interface OrderRepository {

    Integer create(Order order) throws BadRequestException;

    Order getOrderById(Integer orderId) throws ResourceNotFoundException;
}
