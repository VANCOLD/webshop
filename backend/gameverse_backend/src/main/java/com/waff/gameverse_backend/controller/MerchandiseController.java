package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Merchandise;
import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/merch")
public class MerchandiseController {

    @Autowired
    private MerchandiseRepository merchRepo;

    @GetMapping
    public List<Merchandise> findAll() {
        return merchRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Merchandise> createMerchandise(@RequestBody Merchandise merch) {
        merch = (Merchandise) merchRepo.save(merch);
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(merch);
    }
}