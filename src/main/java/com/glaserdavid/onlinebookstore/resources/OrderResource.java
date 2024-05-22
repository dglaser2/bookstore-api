package com.glaserdavid.onlinebookstore.resources;

import com.glaserdavid.onlinebookstore.domain.Order;
import com.glaserdavid.onlinebookstore.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Create an order")
    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @Operation(summary = "Retrieve an order by order_id")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
