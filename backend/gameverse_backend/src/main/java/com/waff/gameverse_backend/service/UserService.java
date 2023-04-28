package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService
{

    private final UserRepository statusRepository;

    public UserService(UserRepository statusRepository) { this.statusRepository = statusRepository; }


    public List<User> findAllUsers() {
        return this.statusRepository.findAll();
    }

    public Optional<User> findUserById(Long uid) {
        return this.statusRepository.findById(uid);
    }

    public Optional<User> deleteUser(Long uid)
    {
        Optional<User> returnUser = this.findUserById(uid);

        if( returnUser.isPresent() ) {
            this.statusRepository.deleteById(uid);
        }

        return returnUser;
    }

    public User saveUser(User status)
    {
        Optional<User> checkUser = this.statusRepository.findById(status.getUid());

        if( checkUser.isEmpty() ) {
            return this.statusRepository.save(status);
        } else {
            return null;
        }
    }
}