package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController
{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping
    public User deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> findUser(@PathVariable Long id){return userService.findUserById(id); }

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

}
