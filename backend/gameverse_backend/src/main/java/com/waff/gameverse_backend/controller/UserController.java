package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserController
{

    private UserService userService;

    @PostMapping("/create")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping("/delete")
    public User deleteUser(@RequestBody User user){
        return userService.deleteUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> findUser(@PathVariable Long uId){return userService.findUserById(uId); }

    @GetMapping
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

}
