package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor @NoArgsConstructor
public class UserService
{
    private UserRepository userRepo;

}
