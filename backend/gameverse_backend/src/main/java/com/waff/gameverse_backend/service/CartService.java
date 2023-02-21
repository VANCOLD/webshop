package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.PositionRepository;
import com.waff.gameverse_backend.repository.StatusRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService
{

    private UserRepository userRepo;

    private CartRepository cartRepo;

    private PositionRepository posRepo;

    private StatusRepository statusRepo;


    public CartService(UserRepository userRepo, CartRepository cartRepo, PositionRepository posRepo, StatusRepository statusRepo)
    {
        this.userRepo   = userRepo;
        this.cartRepo   = cartRepo;
        this.posRepo    = posRepo;
        this.statusRepo = statusRepo;
    }
}
