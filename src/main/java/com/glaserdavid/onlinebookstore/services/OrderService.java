package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;

public interface OrderService {

    Order getOrderById(Integer orderId) throws ResourceNotFoundException;

    Order createOrder(Order order) throws BadRequestException;
}
