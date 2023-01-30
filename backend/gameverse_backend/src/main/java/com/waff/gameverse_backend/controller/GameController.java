package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Game;
import com.waff.gameverse_backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameRepository gameRepo;

    @GetMapping
    public List<Game> findAll() {
        return gameRepo.findAll();
    }
}