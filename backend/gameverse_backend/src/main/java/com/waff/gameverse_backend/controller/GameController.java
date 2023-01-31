package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Game;
import com.waff.gameverse_backend.datamodel.Merchandise;
import com.waff.gameverse_backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        game = (Game) gameRepo.save(game);
        return ResponseEntity.created(URI.create("http://localhost:8080/products")).body(game);
    }
}