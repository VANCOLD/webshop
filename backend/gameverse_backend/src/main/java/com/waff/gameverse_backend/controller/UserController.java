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
            return new ResponseEntity<>("The user-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<?> findUserById(@PathVariable Long uid)
    {
        Optional<User> returnValue = userService.findUserById(uid);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("No user found with the id " + uid, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody Long uid)
    {

        if(uid < 0)
            return new ResponseEntity<>("A user with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<User> returnValue = this.userService.deleteUser(uid);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("The user with the id " + uid + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user)
    {
        if(user.getUid() < 0)
            return new ResponseEntity<>("A user with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        User returnValue = this.userService.saveUser(user);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A user with the id " + user.getUid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Long uid = user.getUid();
        if(uid < 0)
            return new ResponseEntity<>("A user with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<User> findValue = this.userService.findUserById(uid);
        User returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The user with the id " + uid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setUid(uid);
        returnValue.setUsername(user.getUsername());
        returnValue.setPassword(user.getPassword());
        returnValue.setAdmin(user.getAdmin());

        return new ResponseEntity<>( this.userService.saveUser(returnValue), HttpStatus.OK );
    }
}