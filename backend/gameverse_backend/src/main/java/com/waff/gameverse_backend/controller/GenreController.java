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
            return new ResponseEntity<>("The genre-list is empty!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findGereById(@PathVariable Long id)
    {
        Optional<Genre> returnValue = genreService.findGenreById(id);

        if( returnValue.isPresent()  )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("No genre found with the id " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id)
    {

        if(id < 0)
            return new ResponseEntity<>("A genre with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Optional<Genre> returnValue = this.genreService.deleteGenre(id);

        if( returnValue.isPresent() )
            return new ResponseEntity<>(returnValue.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("The genre with the id " + id + " doesn't exist!", HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<?> saveGenre(@RequestBody Genre genre)
    {
        if(genre.getGid() < 0)
            return new ResponseEntity<>("A genre with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);

        Genre returnValue = this.genreService.saveGenre(genre);

        if( returnValue != null )
            return new ResponseEntity<>(returnValue, HttpStatus.OK);
        else
            return new ResponseEntity<>("A genre with the id " + genre.getGid() + " already exists!", HttpStatus.CONFLICT);
    }


    @PutMapping
    public ResponseEntity<?> updateGenre(@RequestBody Genre genre)
    {
        Long gid = genre.getGid();
        if(gid < 0)
            return new ResponseEntity<>("A genre with an id smaller than 0 can not exist!", HttpStatus.CONFLICT);


        Optional<Genre> findValue = this.genreService.findGenreById(gid);
        Genre returnValue;

        if(findValue.isPresent())
            returnValue = findValue.get();
        else
            return new ResponseEntity<>("The genre with the id " + gid + " you are trying to update doesn't exist!", HttpStatus.CONFLICT);

        returnValue.setGid(gid);
        returnValue.setName(genre.getName());

        return new ResponseEntity<>( this.genreService.saveGenre(returnValue), HttpStatus.OK );
    }
}
