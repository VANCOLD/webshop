package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAll() {
        return null;
    }

    @GetMapping("/allByIds")
    public ResponseEntity<List<UserDto>> findAllByIds(@RequestBody List<Long> ids) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<UserDto>> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@RequestBody UserDto userDto) {
        return null;
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        return null;
    }

}
