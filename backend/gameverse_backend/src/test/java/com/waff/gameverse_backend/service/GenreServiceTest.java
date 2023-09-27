package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.GenreDto;
import com.waff.gameverse_backend.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testclass für GenreService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(GenreService.class)
@ActiveProfiles("test")
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die First Person Shooters
        Long genreId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long genreId2 = 1000L;

        var testCase1 = this.genreService.findById(genreId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.genreService.findById(genreId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.genreService.findAll();
        assertThat(testCase1.size()).isEqualTo(15);
    }

    @Test
    void findByNameTest() {

        // Das Genre mit den Namen Playstation 5 existiert
        String genre1 = "First Person Shooters";
        // Das Genre Xbox 360 hingegen nicht!
        String genre2 = "Among Us";

        // Hier erwarten wir das wir das Genre zurück bekommen die wir suchen
        var testCase1 = this.genreService.findByName(genre1);
        assertThat(testCase1.getName()).isEqualTo(genre1);

        // Da es keine Genre mit den Namen edit_kuche gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.genreService.findByName(genre2));
    }

    @Test
    void saveTest() {
        String genre1 = "Real Life";

        String genre2 = "First Person Shooters";


        var testCase1 = this.genreService.save(genre1);
        assertThat(testCase1.getName()).isEqualTo(genre1);

        assertThrows(IllegalArgumentException.class, () -> this.genreService.save(genre2));
    }

    @Test
    void updateTest() {
        GenreDto genre = new GenreDto("test");
        assertThrows(NoSuchElementException.class,() -> this.genreService.update(genre));

        genre.setId(1L);
        var updatedRole = this.genreService.update(genre);
        assertThat(updatedRole.getName()).isEqualTo(genre.getName());

        genre.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.genreService.update(genre));

    }

    @Test
    void deleteTest() {

        // Genre First Person Shooters
        Long genre1 = 1L;

        // Nicht existierendes Genre
        Long genre2 = 1000L;

        var checkGenre = genreService.findById(genre1);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.genreService.delete(genre1);
        assertThat(testCase1.getName()).isEqualTo("First Person Shooters");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.genreService.findById(genre1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Genre mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.genreService.delete(genre2));
    }
}
