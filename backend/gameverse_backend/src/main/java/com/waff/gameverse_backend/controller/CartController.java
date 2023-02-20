package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.service.CartService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CartController
{
    private CartService cartService;
}
