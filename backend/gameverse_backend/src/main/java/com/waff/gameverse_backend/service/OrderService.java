package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.repository.OrderRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userService     = userService;
        this.productRepository  = productRepository;
    }

    /**
     * Find a order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The found order.
     * @throws NoSuchElementException If no order with the given ID exists.
     */
    public Order findById(Long id) {
        return this.orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist"));
    }

    /**
     * Find all orders.
     *
     * @return A list of all orders.
     */
    public List<Order> findAll() {
        return this.orderRepository.findAll();
    }

    /**
     * Save a new order with the given name.
     *
     * @param user the user which will generate the order
     * @return The saved order.
     * @throws IllegalArgumentException If a order with the same name already exists.
     */
    public Order save(UserDto user) {

        return null;
    }

    /**
     * Update a order with the information from the provided OrderDto.
     *
     * @param orderDto The OrderDto containing updated order information.
     * @return The updated order.
     * @throws NoSuchElementException  If the order with the given ID does not exist.
     */
    public Order update(OrderDto orderDto) {
        var toUpdate = this.orderRepository.findById(orderDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist"));

        toUpdate.setUser(new User(orderDto.getUser()));
        toUpdate.setOrderedProducts(orderDto.getOrderedProducts().stream().map(OrderedProduct::new).toList());
        return this.orderRepository.save(toUpdate);
    }

    /**
     * Delete a order with the given ID and update associated roles.
     *
     * @param id The ID of the order to delete.
     * @return The deleted order.
     * @throws NoSuchElementException If the order with the given ID does not exist.
     */
    public Order delete(Long id) {
        return null;
    }
}
