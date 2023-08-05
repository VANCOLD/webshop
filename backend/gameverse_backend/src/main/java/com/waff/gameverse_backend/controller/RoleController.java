package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/role")
@RestController
public class RoleController {

    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> findAll() {
        return null;
    }

    @GetMapping("/allByIds")
    public ResponseEntity<List<RoleDto>> findAllByIds(@RequestBody List<Long> ids) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<RoleDto>> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto roleDto) {
        return null;
    }

    @PutMapping
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto roleDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDto> delete(@PathVariable Long id) {
        return null;
    }
}
