package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Merchandise;
import com.waff.gameverse_backend.repository.MerchandiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}