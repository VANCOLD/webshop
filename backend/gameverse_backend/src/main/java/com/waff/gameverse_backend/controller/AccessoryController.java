package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Accessory;
import com.waff.gameverse_backend.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/accessories")
public class AccessoryController
{

    @Autowired
    private AccessoryRepository accRepo;

    @GetMapping
    public List<Accessory> findAll() {
        return accRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Accessory> createAccessory(@RequestBody Accessory accessory) {
        accessory = (Accessory) accRepo.save(accessory);
        return ResponseEntity.created(URI.create("http://localhost:8080/consoles")).body(accessory);
    }
}
