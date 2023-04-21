package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.datamodel.Genre;
import com.waff.gameverse_backend.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) { this.genreRepository = genreRepository;}


    public Optional<Genre> findGenreById(Long id) { return this.genreRepository.findById(id); }

    public List<Genre> findAllGenres() { return this.genreRepository.findAll(); }

    public Optional<Genre> deleteGenre(Long id)
    {
        Optional<Genre> returnGenre = this.findGenreById(id);

        if( returnGenre.isPresent() ) {
            this.genreRepository.deleteById(id);
        }

        return returnGenre;
    }

    public Genre saveGenre(Genre genre)
    {
        Optional<Genre> returnGenre = this.genreRepository.findById(genre.getGid());

        if( returnGenre.isEmpty() ) {
            return this.genreRepository.save(genre);
        } else {
            return null;
        }
    }
}
