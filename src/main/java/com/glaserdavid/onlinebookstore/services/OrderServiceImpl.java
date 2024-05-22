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
        try {
            return orderRepository.getOrderById(orderId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order with id " + orderId + " not found");
        }
    }

    @Override
    public Order createOrder(Order order) throws BadRequestException {
        try {
            Integer orderId = orderRepository.create(order);
            return orderRepository.getOrderById(orderId);
        } catch (BadRequestException e) {
            throw new BadRequestException("Invalid order details: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Order created but failed to retrieve with id " + order.getOrderId());
        }
    }
}
