package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.PositionRepository;
import com.waff.gameverse_backend.repository.StatusRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @NoArgsConstructor
public class CartService
{

    private UserRepository userRepo;

    private CartRepository cartRepository;

    private PositionRepository posRepo;

    private StatusRepository statusRepo;
}
