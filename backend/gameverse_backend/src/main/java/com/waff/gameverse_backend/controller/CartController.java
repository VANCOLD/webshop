package com.waff.gameverse_backend.controller;
import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.CartItem;
import com.waff.gameverse_backend.service.CartService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody AddProductToCartDto requestDto) {
        Long userId = requestDto.getUserid();
        Long productId = requestDto.getProductid();

        // Call the CartService to add the product to the user's cart
        Cart updatedCart = cartService.addToCart(userId, productId);

        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/get")
    public ResponseEntity<Cart> getCartContents(@RequestParam Long userId) {
        // Call the CartService to get the user's cart contents
        Cart userCart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(userCart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        // Call the CartService to remove the product from the user's cart
        Cart updatedCart = cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        // Call the CartService to clear the user's cart
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCartItemQuantity(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        // Call the CartService to update the quantity of the product in the user's cart
        Cart updatedCart = cartService.updateCartItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> calculateCartTotal(@RequestParam Long userId) {
        // Call the CartService to calculate the total price of the user's cart
        Double total = cartService.calculateCartTotal(userId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount(@RequestParam Long userId) {
        // Call the CartService to get the total number of items in the user's cart
        Integer itemCount = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(itemCount);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long userId) {
        // Call the CartService to get the list of cart items in the user's cart
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts() {
        // Call the CartService to get a list of all carts
        List<Cart> allCarts = cartService.getAll();
        return ResponseEntity.ok(allCarts);
    }
}
