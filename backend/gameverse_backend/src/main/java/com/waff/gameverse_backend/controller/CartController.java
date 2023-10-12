package com.waff.gameverse_backend.controller;
import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.dto.CartDto;
import com.waff.gameverse_backend.dto.SimpleProductDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.service.CartService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PutMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestBody AddProductToCartDto requestDto) {

        // Call the CartService to add the product to the user's cart
        Cart updatedCart = cartService.addToCart(requestDto);

        return ResponseEntity.ok(updatedCart.convertToDto());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        // Call the CartService to get the user's cart contents
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(userCart.convertToDto());
    }

    @PutMapping("/remove")
    public ResponseEntity<CartDto> removeFromCart(@Validated @RequestBody AddProductToCartDto requestDto) {
        // Call the CartService to remove the product from the user's cart
        Cart updatedCart = cartService.removeFromCart(requestDto);
        return ResponseEntity.ok(updatedCart.convertToDto());
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        // Call the CartService to clear the user's cart
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<Double> calculateCartTotal(@PathVariable Long userId) {
        // Call the CartService to calculate the total price of the user's cart
        Double total = cartService.calculateCartTotal(userId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> getCartItemCount(@PathVariable Long userId) {
        // Call the CartService to get the total number of items in the user's cart
        Integer itemCount = cartService.getProductCount(userId);
        return ResponseEntity.ok(itemCount);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartDto>> getAll() {
        // Call the CartService to get a list of all carts
        List<Cart> allCarts = cartService.getAll();
        return ResponseEntity.ok(allCarts.stream().map(Cart::convertToDto).toList());
    }

}

