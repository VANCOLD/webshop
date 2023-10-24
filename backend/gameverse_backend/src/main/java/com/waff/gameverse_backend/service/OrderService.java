package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.OrderStatus;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.repository.OrderRepository;
import com.waff.gameverse_backend.repository.OrderedProductRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderedProductRepository orderedProductRepository;

    public OrderService(OrderRepository orderRepository, UserService userService, UserRepository userRepository, ProductRepository productRepository, OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.userService     = userService;
        this.userRepository = userRepository;
        this.productRepository  = productRepository;
        this.orderedProductRepository = orderedProductRepository;
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
        User toOrder = userService.findById(user.getId());

        Order order  = new Order();

        Cart userCart = toOrder.getCart();
        if(userCart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Der Einkaufswagen ist leer!");
        }

        order.setOrderStatus(OrderStatus.ORDERED);
        order.setUser(toOrder);

        var orderedItems = new ArrayList<OrderedProduct>();
        order.setOrderedProducts(orderedItems);
        populateOrder(order, userCart);
        orderRepository.save(order);

        toOrder.getCart().getProducts().clear();
        userRepository.save(toOrder);
        return order;

    }

    private void populateOrder(Order order, Cart userCart) {

        var orderedItems = new ArrayList<OrderedProduct>();

        for(Product toConvert : new HashSet<>(userCart.getProducts())) {

            int productAmount = toConvert.getStock();
            int orderAmount   = userCart.getProducts().stream().filter(product -> product.equals(toConvert)).toList().size();

            if(orderAmount > productAmount && productAmount - orderAmount > -1) {
                throw new IllegalStateException("Die Bestellmenge von " + toConvert.getName() + " ist " + orderAmount + "!\n Die Stückanzahl im Lager ist aber " + productAmount);
            }

            OrderedProduct orderedProduct = new OrderedProduct();
            orderedProduct.setAmount(orderAmount);
            orderedProduct.setTax(toConvert.getTax());
            orderedProduct.setName(toConvert.getName());
            orderedProduct.setPrice(toConvert.getPrice() * orderAmount);
            orderedProduct.setDescription(toConvert.getDescription());
            orderedProduct.setProduct(toConvert);
            orderedProduct.setOrder(order);

            orderedItems.add(orderedProduct);
            orderedProductRepository.save(orderedProduct);

        }

        for(Product toConvert : new HashSet<>(userCart.getProducts()) ) {

            int orderAmount   = userCart.getProducts().stream().filter(product -> product.equals(toConvert)).toList().size();
            toConvert.setStock(toConvert.getStock() - orderAmount);

        }

        order.setOrderedProducts(orderedItems);
    }


    /**
     * Delete a order with the given ID and update associated roles.
     *
     * @param id The ID of the order to delete.
     * @return The deleted order.
     * @throws NoSuchElementException If the order with the given ID does not exist.
     */
    public Order delete(Long id) {

        var toDelete = this.orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist"));

        this.resetProducts(toDelete.getOrderedProducts());
        toDelete.getUser().getOrders().remove(toDelete);
        toDelete.setUser(null);
        orderedProductRepository.deleteAll(toDelete.getOrderedProducts());
        toDelete.setOrderedProducts(null);
        orderRepository.delete(toDelete);

        return toDelete;
    }

    private void resetProducts(List<OrderedProduct> orderedProducts) {
        for(OrderedProduct orderedItem : orderedProducts) {
            var product = orderedItem.getProduct();
            product.setStock(product.getStock() + orderedItem.getAmount());
            productRepository.save(product);
        }
    }

    public Order update(OrderDto orderDto) {
        var toUpdate = this.orderRepository.findById(orderDto.getId()).orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist!"));

        toUpdate.setOrderStatus(OrderStatus.getOrderStatus(orderDto.getOrderStatus()));
        return this.orderRepository.save(toUpdate);
    }
}
