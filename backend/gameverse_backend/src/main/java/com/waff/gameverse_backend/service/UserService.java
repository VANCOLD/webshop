package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    private UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user){
        return userRepo.save(user);
    }

    public User deleteUser(User user){
         userRepo.delete(user);
         return user;
    }

    public Optional<User> findUserById(Long uId){
        return userRepo.findById(uId);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

}
