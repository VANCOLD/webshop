package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.service.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController
{
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
}
