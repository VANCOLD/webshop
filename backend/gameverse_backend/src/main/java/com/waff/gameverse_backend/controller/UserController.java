package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.*;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.Order;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.service.CartService;
import com.waff.gameverse_backend.service.OrderService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The UserController class handles operations related to user management.
 */
@EnableMethodSecurity
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    private final CartService cartService;

    private final OrderService orderService;

    /**
     * Constructs a new UserController with the provided UserService.
     *
     * @param userService The UserService to use for managing users.
     */
    public UserController(UserService userService, CartService cartService, OrderService orderService) {
        this.userService    = userService;
        this.cartService    = cartService;
        this.orderService   = orderService;
    }

    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity<List<UserDto>> A ResponseEntity containing a list of UserDto objects.
     * @see UserDto
     */
    @GetMapping("/all")
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<List<SimpleUserDto>> findAll() {
        var users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users.stream().map(User::convertToSimpleDto).toList());
        }
    }


    /**
     * Retrieves a list of all users.
     *
     * @return ResponseEntity<List<UserDto>> A ResponseEntity containing a list of UserDto objects.
     * @see UserDto
     */
    @GetMapping("/all/full")
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<List<UserDto>> findAllFullUser() {
        var users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users.stream().map(User::convertToDto).toList());
        }
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the UserDto for the specified ID.
     * @throws NoSuchElementException if the user with the given ID does not exist.
     * @see UserDto
     */
    @GetMapping("/{id}")
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<SimpleUserDto> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id).convertToSimpleDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the UserDto for the specified ID.
     * @throws NoSuchElementException if the user with the given ID does not exist.
     * @see UserDto
     */
    @GetMapping("/full/{id}")
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<UserDto> findFullUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }


    /**
     * Creates a new user.
     *
     * @param userDto The SimpleUserDto containing the user information to be created.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the newly created UserDto.
     * @throws IllegalArgumentException if there is a conflict or error while creating the user.
     * @see SimpleUserDto
     */
    @PostMapping
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<SimpleUserDto> save(@Validated @RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.save(userDto).convertToSimpleDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates an existing user.
     *
     * @param userDto The UserDto containing the updated user information.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the updated UserDto.
     * @throws NoSuchElementException if the user to update does not exist.
     * @see UserDto
     */
    @PutMapping
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<UserDto> update(@Validated @RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.update(userDto).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return ResponseEntity<UserDto> A ResponseEntity containing the deleted UserDto.
     * @throws NoSuchElementException if the user with the given ID does not exist.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@tokenService.hasPrivilege('edit_users')")
    public ResponseEntity<String> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        try {
            if (!userService.findByUsername(jwt.getSubject()).getId().equals(id)) {
                this.userService.delete(id);
                return ResponseEntity.ok("User with id " + id + " deleted");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User der gelöscht werden soll is eingeloggte User!\nIllegale Aktion");
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/addToCart/{productId}")
    @PreAuthorize("@tokenService.hasPrivilege('view_carts')")
    public ResponseEntity<CartDto> addToCart(@AuthenticationPrincipal Jwt jwt, @PathVariable Long productId) {

        Long userId  = userService.findByUsername(jwt.getSubject()).getId();
        // Call the CartService to add the product to the user's cart
        Cart updatedCart = cartService.addToCart(new AddProductToCartDto(productId, userId));

        return ResponseEntity.ok(updatedCart.convertToDto());
    }

    @GetMapping("/cart")
    @PreAuthorize("@tokenService.hasPrivilege('view_carts')")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal Jwt jwt) {
        Cart cart = userService.findByUsername(jwt.getSubject()).getCart();
        return ResponseEntity.ok(cart == null ? null : cart.convertToDto());

    }

    @PutMapping("/removeFromCart/{productId}")
    @PreAuthorize("@tokenService.hasPrivilege('view_carts')")
    public ResponseEntity<CartDto> removeFromCart(@AuthenticationPrincipal Jwt jwt, @PathVariable Long productId) {

        Long userId  = userService.findByUsername(jwt.getSubject()).getId();
        Cart updatedCart = cartService.removeFromCart(new AddProductToCartDto(productId, userId));
        return ResponseEntity.ok(updatedCart.convertToDto());
    }

    @GetMapping("/orders")
    @PreAuthorize("@tokenService.hasPrivilege('view_orders')")
    public ResponseEntity<List<OrderDto>> getOrders(@AuthenticationPrincipal Jwt jwt) {
        List<Order> orders = userService.findByUsername(jwt.getSubject()).getOrders();

        if(orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders.stream().map(Order::convertToDto).toList());
        }
    }

    @PostMapping("/saveOrder")
    @PreAuthorize("@tokenService.hasPrivilege('view_orders')")
    public ResponseEntity<OrderDto> save(@AuthenticationPrincipal Jwt jwt) {
        User user = userService.findByUsername(jwt.getSubject());

        if(this.userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.ok(this.orderService.save(user.convertToDto()).convertToDto());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/me")
    @PreAuthorize("@tokenService.hasPrivilege('view_profile')")
    public ResponseEntity<UserDto> loggedInUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(userService.findByUsername(jwt.getSubject()).convertToDto());
    }

}
