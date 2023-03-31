package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.Cart;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.PositionRepository;
import com.waff.gameverse_backend.repository.StatusRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService
{

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private PositionRepository posRepo;

    @Autowired
    private StatusRepository statusRepo;

    public List<Cart> findAllCarts() {
        return this.cartRepo.findAll();
    }

    public Optional<Cart> findUserById(Long id) {
        return this.cartRepo.findById(id);
    }

    public Cart deleteCart(Cart cart) {
        this.cartRepo.delete(cart);
        return cart;
    }

    public Cart createCart(Cart cart) {
        return this.cartRepo.save(cart);
    }
}
