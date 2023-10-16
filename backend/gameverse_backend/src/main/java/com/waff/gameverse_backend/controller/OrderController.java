package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.Order;
import com.waff.gameverse_backend.service.OrderService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_orders')")
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService  = userService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findById(Long orderId) {
        try {
            Order order = this.orderService.findById(orderId);
            return ResponseEntity.ok(order.convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> findAll() {

        List<Order> orders = this.orderService.findAll();

        if(orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders.stream().map(Order::convertToDto).toList());
        }
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(UserDto user) {
        if(this.userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.ok(this.orderService.save(user).convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/confirm")
    public ResponseEntity<OrderDto> confirm(OrderDto orderDto) {
        var toCheck = this.orderService.findById(orderDto.getId());

        if(toCheck != null) {

            Order order = this.orderService.confirm(orderDto);
            return ResponseEntity.ok(order.convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancel(OrderDto orderDto) {
        var toCheck = this.orderService.findById(orderDto.getId());

        if(toCheck != null) {

            Order order = this.orderService.cancel(orderDto);
            return ResponseEntity.ok(order.convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<OrderDto> delete(Long orderId) {
        var toCheck = this.orderService.findById(orderId);

        if(toCheck != null) {

            Order order = this.orderService.delete(orderId);
            return ResponseEntity.ok(order.convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
