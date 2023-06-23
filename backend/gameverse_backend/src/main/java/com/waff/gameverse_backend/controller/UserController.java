package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.User;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<?> findAllUser()
    {
        List<User> response = this.userService.findAllUsers();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<?> findUserById(@PathVariable Long uid)
    {
        Optional<User> returnValue = userService.findUserById(uid);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the id " + uid);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody Long uid)
    {

        if(uid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with an id smaller than 0 can not exist!");

        Optional<User> returnValue = this.userService.deleteUser(uid);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The user with the id " + uid + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user)
    {
        if(user.getUid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with an id smaller than 0 can not exist!");

        User returnValue = this.userService.saveUser(user);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with the id " + user.getUid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Long uid = user.getUid();
        if(uid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with an id smaller than 0 can not exist!");


        Optional<User> findValue = this.userService.findUserById(uid);
        User returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user with the id " + uid + " you are trying to update doesn't exist!");

        returnValue.setUid(uid);
        returnValue.setUsername(user.getUsername());
        returnValue.setPassword(user.getPassword());
        returnValue.setRoles(user.getRoles());

        return ResponseEntity.ok(this.userService.saveUser(returnValue));
    }
}