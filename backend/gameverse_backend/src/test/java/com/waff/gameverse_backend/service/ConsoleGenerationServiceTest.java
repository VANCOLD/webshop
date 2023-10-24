package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ConsoleGenerationDto;
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
 * Testclass für ConsoleGenerationService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(ConsoleGenerationService.class)
@ActiveProfiles("test")
public class ConsoleGenerationServiceTest {

    @Autowired
    private ConsoleGenerationService consoleGenerationService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die user Rolle
        Long consoleGenerationId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long consoleGenerationId2 = 1000L;

        var testCase1 = this.consoleGenerationService.findById(consoleGenerationId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.consoleGenerationService.findById(consoleGenerationId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.consoleGenerationService.findAll();
        assertThat(testCase1.size()).isEqualTo(5);
    }

    @Test
    void findByNameTest() {

        // Das ConsoleGeneration mit den Namen Playstation 5 existiert
        String consoleGeneration1 = "Playstation 5";
        // Das ConsoleGeneration Xbox 360 hingegen nicht!
        String consoleGeneration2 = "Xbox 360";

        // Hier erwarten wir das wir das ConsoleGeneration zurück bekommen die wir suchen
        var testCase1 = this.consoleGenerationService.findByName(consoleGeneration1);
        assertThat(testCase1.getName()).isEqualTo(consoleGeneration1);

        // Da es keine ConsoleGeneration mit den Namen edit_kuche gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.consoleGenerationService.findByName(consoleGeneration2));
    }

    @Test
    void saveTest() {
        String consoleGeneration1 = "Xbox 360";

        String consoleGeneration2 = "Playstation 5";


        var testCase1 = this.consoleGenerationService.save(consoleGeneration1);
        assertThat(testCase1.getName()).isEqualTo(consoleGeneration1);

        assertThrows(IllegalArgumentException.class, () -> this.consoleGenerationService.save(consoleGeneration2));
    }

    @Test
    void updateTest() {
        ConsoleGenerationDto consoleGeneration = new ConsoleGenerationDto(100L, "test");
        assertThrows(NoSuchElementException.class,() -> this.consoleGenerationService.update(consoleGeneration));

        consoleGeneration.setId(1L);
        var updatedRole = this.consoleGenerationService.update(consoleGeneration);
        assertThat(updatedRole.getName()).isEqualTo(consoleGeneration.getName());

        consoleGeneration.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.consoleGenerationService.update(consoleGeneration));

    }

    @Test
    void deleteTest() {

        // ConsoleGeneration view_profile
        Long consoleGeneration1 = 1L;

        // Nicht existierendes ConsoleGeneration
        Long consoleGeneration2 = 1000L;

        var checkConsoleGeneration = consoleGenerationService.findById(consoleGeneration1);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.consoleGenerationService.delete(consoleGeneration1);
        assertThat(testCase1.getName()).isEqualTo("XBox Series X");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.consoleGenerationService.findById(consoleGeneration1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein ConsoleGeneration mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.consoleGenerationService.delete(consoleGeneration2));
    }
}
