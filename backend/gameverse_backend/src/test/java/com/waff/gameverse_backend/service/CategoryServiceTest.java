package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.CategoryDto;
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
 * Testclass für CategoryService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(CategoryService.class)
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die Kategorie Games
        Long categoryId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long categoryId2 = 1000L;

        var testCase1 = this.categoryService.findById(categoryId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.categoryService.findById(categoryId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.categoryService.findAll();
        assertThat(testCase1.size()).isEqualTo(6);
    }

    @Test
    void findByNameTest() {

        // Das Category mit den Namen Playstation 5 existiert
        String category1 = "Games";
        // Das Category Xbox 360 hingegen nicht!
        String category2 = "Schokolade";

        // Hier erwarten wir das wir das Category zurück bekommen die wir suchen
        var testCase1 = this.categoryService.findByName(category1);
        assertThat(testCase1.getName()).isEqualTo(category1);

        // Da es keine Category mit den Namen edit_kuche gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.categoryService.findByName(category2));
    }

    @Test
    void saveTest() {
        String category1 = "Gamer Bathwater";

        String category2 = "Games";


        var testCase1 = this.categoryService.save(category1);
        assertThat(testCase1.getName()).isEqualTo(category1);

        assertThrows(IllegalArgumentException.class, () -> this.categoryService.save(category2));
    }

    @Test
    void updateTest() {
        CategoryDto category = new CategoryDto(100L, "test");
        assertThrows(NoSuchElementException.class,() -> this.categoryService.update(category));

        category.setId(1L);
        var updatedRole = this.categoryService.update(category);
        assertThat(updatedRole.getName()).isEqualTo(category.getName());

        category.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.categoryService.update(category));

    }

    @Test
    void deleteTest() {

        // Category Games
        Long category1 = 1L;

        // Nicht existierendes Category
        Long category2 = 1000L;

        var checkCategory = categoryService.findById(category1);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.categoryService.delete(category1);
        assertThat(testCase1.getName()).isEqualTo("Games");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.categoryService.findById(category1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Category mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.categoryService.delete(category2));
    }
}
