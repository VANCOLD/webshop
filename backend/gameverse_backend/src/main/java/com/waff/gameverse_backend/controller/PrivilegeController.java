package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.service.PrivilegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@PreAuthorize("@tokenService.hasPrivilege('edit_users')")
@RequestMapping("/api/privileges")
@RestController
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }
    @GetMapping("/all")
    public ResponseEntity<Set<PrivilegeDto>> findAll() {

        var roles = privilegeService.findAll();

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(roles.stream().map(Privilege::convertToDto).toList()));
        }
    }

    @GetMapping("/allByIds")
    public ResponseEntity<Set<PrivilegeDto>> findAllByIds(@RequestBody List<Long> ids) {
        var roles = privilegeService.findAllByIds(ids);

        if(roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(new HashSet<>(roles.stream().map(Privilege::convertToDto).toList()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeDto> findById(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(privilegeService.findById(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.noContent().build();
        }

    }

    @PostMapping
    public ResponseEntity<PrivilegeDto> save(@RequestBody PrivilegeDto roleDto) {
        try {
            return ResponseEntity.ok(privilegeService.save(roleDto.getName()).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<PrivilegeDto> update(@RequestBody PrivilegeDto privilegeDto) {
        try {
            return ResponseEntity.ok(privilegeService.update(privilegeDto).convertToDto());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PrivilegeDto> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(privilegeService.delete(id).convertToDto());
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
