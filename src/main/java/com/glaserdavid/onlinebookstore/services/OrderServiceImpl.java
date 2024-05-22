package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.exceptions.BadRequestException;
import com.glaserdavid.onlinebookstore.exceptions.ResourceNotFoundException;
import com.glaserdavid.onlinebookstore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order getOrderById(Integer orderId) throws ResourceNotFoundException {
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public Order createOrder(Order order) throws BadRequestException {
        Integer orderId = orderRepository.create(order);
        return orderRepository.getOrderById(orderId);
    }
}
