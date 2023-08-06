package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Set<UserDto>> findAll() {

        var users = userService.findAll();

        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(users.stream().map(User::convertToDto).toList()));
        }
    }

    @GetMapping("/allByIds")
    public ResponseEntity<Set<UserDto>> findAllByIds(@RequestBody List<Long> ids) {
        var users = userService.findAllByIds(ids);

        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(users.stream().map(User::convertToDto).toList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(userService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.save(userDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.update(userDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
