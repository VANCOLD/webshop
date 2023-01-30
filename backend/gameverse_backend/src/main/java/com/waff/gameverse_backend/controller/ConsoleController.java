package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Console;
import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.ConsoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consoles")
public class ConsoleController {

    @Autowired
    private ConsoleRepository conRepo;

    @GetMapping
    public List<Console> findAll() {
        return conRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Console> createProduct(@RequestBody Console console) {
        console = (Console) conRepo.save(console);
        return ResponseEntity.created(URI.create("http://localhost:8080/consoles")).body(console);
    }
}
