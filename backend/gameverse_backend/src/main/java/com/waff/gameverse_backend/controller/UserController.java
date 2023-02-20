package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserController
{

    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid User user){
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
