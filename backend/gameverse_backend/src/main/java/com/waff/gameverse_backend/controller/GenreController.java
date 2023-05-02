package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.datamodel.Genre;
import com.waff.gameverse_backend.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<?> findAllGenres()
    {
        List<Genre> response = this.genreService.findAllGenres();

        if (response.isEmpty() )
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre-list is empty!");
        else
            return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Genre> returnValue = genreService.findGenreById(id);

        if( returnValue.isPresent()  )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No genre found with the id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id)
    {

        if(id < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A genre with an id smaller than 0 can not exist!");

        Optional<Genre> returnValue = this.genreService.deleteGenre(id);

        if( returnValue.isPresent() )
            return ResponseEntity.ok(returnValue.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The genre with the id " + id + " doesn't exist!");
    }


    @PostMapping
    public ResponseEntity<?> saveGenre(@RequestBody Genre genre)
    {
        if(genre.getGid() < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A genre with an id smaller than 0 can not exist!");

        Genre returnValue = this.genreService.saveGenre(genre);

        if( returnValue != null )
            return ResponseEntity.ok(returnValue);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A genre with the id " + genre.getGid() + " already exists!");
    }


    @PutMapping
    public ResponseEntity<?> updateGenre(@RequestBody Genre genre)
    {
        Long gid = genre.getGid();
        if(gid < 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A genre with an id smaller than 0 can not exist!");


        Optional<Genre> findValue = this.genreService.findGenreById(gid);
        Genre returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The genre with the id " + gid + " you are trying to update doesn't exist!");

        returnValue.setGid(gid);
        returnValue.setName(genre.getName());

        return ResponseEntity.ok( this.genreService.saveGenre(returnValue));
    }
}
