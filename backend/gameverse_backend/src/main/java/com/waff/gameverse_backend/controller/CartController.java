package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Cart;
import com.waff.gameverse_backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/carts")
public class CartController
{
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart(@RequestBody @Valid Cart cart) {
        return cartService.createCart(cart);
    }

    @DeleteMapping
    public Cart deleteCart(@RequestBody @Valid Cart cart) {
        return cartService.deleteCart(cart);
    }

    @GetMapping("/{cartid}")
    public Optional<Cart> findCart(@PathVariable Long cartid) {
        return cartService.findUserById(cartid);
    }

    @GetMapping
    public List<Cart> findAllCarts(){
        return cartService.findAllCarts();
    }
}