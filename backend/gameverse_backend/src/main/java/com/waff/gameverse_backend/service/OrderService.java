package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.OrderDto;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.enums.OrderStatus;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.repository.OrderRepository;
import com.waff.gameverse_backend.repository.OrderedProductRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ProductRepository productRepository;

    private final OrderedProductRepository orderedProductRepository;

    public OrderService(OrderRepository orderRepository, UserService userService, ProductRepository productRepository, OrderedProductRepository orderedProductRepository) {
        this.orderRepository = orderRepository;
        this.userService     = userService;
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

        if(toOrder.getOrders().stream().anyMatch(order -> order.getOrderStatus() == OrderStatus.IN_PROGRESS)) {
            throw new IllegalStateException("Es gibt schon eine Bestellung in Bearbeitung! Bitte diese vorher abschließen");
        }

        Order order  = new Order();

        Cart userCart = toOrder.getCart();
        if(userCart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Der Einkaufswagen ist leer!");
        }

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        order.setUser(toOrder);

        var orderedItems = new ArrayList<OrderedProduct>();

        order.setOrderedProducts(orderedItems);

        for(Product toConvert : userCart.getProducts()) {

            int productAmount = toConvert.getStock();
            int orderAmount   = userCart.getProducts().stream().filter(product -> product.equals(toConvert)).toList().size();

            if(orderAmount > productAmount) {
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

            this.orderedProductRepository.save(orderedProduct);
            orderedItems.add(orderedProduct);

        }

        for(Product toConvert : userCart.getProducts()) {

            int orderAmount   = userCart.getProducts().stream().filter(product -> product.equals(toConvert)).toList().size();

            toConvert.setStock(toConvert.getStock() - orderAmount);
            productRepository.save(toConvert);

        }

        order.setOrderedProducts(orderedItems);
        orderRepository.save(order);
        return order;

    }

    /**
     * Update a order with the information from the provided OrderDto.
     *
     * @param orderDto The OrderDto containing updated order information.
     * @return The updated order.
     * @throws NoSuchElementException  If the order with the given ID does not exist.
     */
    public Order confirm(OrderDto orderDto) {
        var toUpdate = this.orderRepository.findById(orderDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist"));

        if(toUpdate.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Bestellung ist bestätigt, kann nicht nochmal bestätigt werden!");
        }

        toUpdate.setOrderStatus(OrderStatus.COMPLETED);
        toUpdate.getUser().getCart().setProducts(new ArrayList<>());
        return this.orderRepository.save(toUpdate);
    }

    public Order cancel(OrderDto orderDto) {
        var toUpdate = this.orderRepository.findById(orderDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Order with the given ID does not exist"));

        if(toUpdate.getOrderStatus() == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Bestellung ist fertig, kann nicht abgebrochen werden!");
        }

        toUpdate.setUser(null);
        this.orderRepository.save(toUpdate);

        for(OrderedProduct orderedProduct : toUpdate.getOrderedProducts()) {
            var product = orderedProduct.getProduct();
            product.setStock(product.getStock() + orderedProduct.getAmount());
            orderedProduct.setOrder(null);
            orderedProduct.setProduct(null);
            this.orderedProductRepository.delete(orderedProduct);
        }

        this.orderRepository.delete(toUpdate);
        return toUpdate;
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

        orderRepository.delete(toDelete);
        return toDelete;
    }

    public Order findByUserAndOrderStatus(User user, OrderStatus orderStatus) {
        return this.orderRepository.findByUserAndOrderStatusEquals(user, orderStatus).orElseThrow(() -> new NoSuchElementException("Keine offene Bestellung gefunden!"));
    }
}
