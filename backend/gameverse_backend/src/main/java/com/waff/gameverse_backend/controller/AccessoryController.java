package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Accessory;
import com.waff.gameverse_backend.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
