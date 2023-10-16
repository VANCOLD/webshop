package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableMethodSecurity
@PreAuthorize("@tokenService.hasPrivilege('edit_orders')")
@RequestMapping("/api/orders")
@RestController
public class OrderController {

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> findById(Long orderId) {
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> findAll() {
        return null;
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(UserDto user) {
        return null;
    }

    @PutMapping("/confirm")
    public ResponseEntity<OrderDto> confirm(OrderDto orderDto) {
        return null;
    }

    @PutMapping("/cancel")
    public ResponseEntity<OrderDto> cancel(OrderDto orderDto) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<OrderDto> delete(Long id) {
        return null;
    }
}
