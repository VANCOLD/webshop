package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.OrderStatus;
import com.waff.gameverse_backend.model.Order;
import com.waff.gameverse_backend.service.OrderService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public ResponseEntity<OrderDto> findById(@PathVariable Long orderId) {
        try {
            Order order = this.orderService.findById(orderId);
            return ResponseEntity.ok(order.convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/orderstatus")
    public ResponseEntity<List<String>> getAllOrderStatus() {
        return ResponseEntity.ok(Arrays.stream(OrderStatus.values()).toList().stream().map(OrderStatus::getName).toList());
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
    public ResponseEntity<OrderDto> save(@Validated @RequestBody UserDto user) {
        if(this.userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.ok(this.orderService.save(user).convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<OrderDto> update(@Validated @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(this.orderService.update(orderDto).convertToDto());
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> delete(@PathVariable Long orderId) {
        var toCheck = this.orderService.findById(orderId);

        if(toCheck != null) {

            this.orderService.delete(orderId);
            return ResponseEntity.ok("Order erfolgreich gelöscht");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
