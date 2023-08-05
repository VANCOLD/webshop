package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/privilege")
@RestController
public class PrivilegeController {

    @GetMapping("/all")
    public ResponseEntity<List<PrivilegeDto>> findAll() {
        return null;
    }

    @GetMapping("/allByIds")
    public ResponseEntity<List<PrivilegeDto>> findAllByIds(@RequestBody List<Long> ids) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PrivilegeDto>> findById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<PrivilegeDto> save(@RequestBody PrivilegeDto privilegeDto) {
        return null;
    }

    @PutMapping
    public ResponseEntity<PrivilegeDto> update(@RequestBody PrivilegeDto privilegeDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PrivilegeDto> delete(@PathVariable Long id) {
        return null;
    }
}
