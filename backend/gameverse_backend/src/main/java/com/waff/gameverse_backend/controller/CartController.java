package com.waff.gameverse_backend.controller;
import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.service.CartService;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

        // Call the UserService to get the current user (you need to create UserService if not already done)
        User User = userService.findById(userId);

        // Call the CartService to add the product to the user's cart
        Cart updatedCart = cartService.addToCart(User.getId(), productId);

        return ResponseEntity.ok(updatedCart);
    }



}
