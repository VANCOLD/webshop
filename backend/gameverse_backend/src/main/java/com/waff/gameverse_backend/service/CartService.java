package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
}
