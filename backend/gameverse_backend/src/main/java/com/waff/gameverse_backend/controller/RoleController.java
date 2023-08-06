package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.model.Role;
import com.waff.gameverse_backend.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/roles")
@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @GetMapping("/all")
    public ResponseEntity<Set<RoleDto>> findAll() {

        var roles = roleService.findAll();

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(roles.stream().map(Role::convertToDto).toList()));
        }
    }

    @GetMapping("/allByIds")
    public ResponseEntity<Set<RoleDto>> findAllByIds(@RequestBody List<Long> ids) {
        var roles = roleService.findAllByIds(ids);

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(roles.stream().map(Role::convertToDto).toList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(roleService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<RoleDto> save(@RequestBody RoleDto roleDto) {
        try {
            return ResponseEntity.ok(roleService.save(roleDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto roleDto) {
        try {
            return ResponseEntity.ok(roleService.update(roleDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(roleService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
