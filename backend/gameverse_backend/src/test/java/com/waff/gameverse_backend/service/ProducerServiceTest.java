package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.ProducerDto;
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
 * Testclass für ProducerService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(ProducerService.class)
@ActiveProfiles("test")
public class ProducerServiceTest {

    @Autowired
    private ProducerService producerService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die user Rolle
        Long producerId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long producerId2 = 1000L;

        var testCase1 = this.producerService.findById(producerId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.producerService.findById(producerId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.producerService.findAll();
        assertThat(testCase1.size()).isEqualTo(10);
    }

    @Test
    void findByNameTest() {

        // Das Producer mit den Namen Playstation 5 existiert
        String producer1 = "Sony";
        // Das Producer Xbox 360 hingegen nicht!
        String producer2 = "Rare";

        // Hier erwarten wir das wir das Producer zurück bekommen die wir suchen
        var testCase1 = this.producerService.findByName(producer1);
        assertThat(testCase1.getName()).isEqualTo(producer1);

        // Da es keine Producer mit den Namen edit_kuche gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.producerService.findByName(producer2));
    }

    @Test
    void saveTest() {
        String producer1 = "Bethesda";

        String producer2 = "Nintendo";


        var testCase1 = this.producerService.save(producer1);
        assertThat(testCase1.getName()).isEqualTo(producer1);

        assertThrows(IllegalArgumentException.class, () -> this.producerService.save(producer2));
    }

    @Test
    void updateTest() {
        ProducerDto producer = new ProducerDto("test");
        assertThrows(NoSuchElementException.class,() -> this.producerService.update(producer));

        producer.setId(1L);
        var updatedRole = this.producerService.update(producer);
        assertThat(updatedRole.getName()).isEqualTo(producer.getName());

        producer.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.producerService.update(producer));

    }

    @Test
    void deleteTest() {

        // Producer view_profile
        Long producer1 = 1L;

        // Nicht existierendes Producer
        Long producer2 = 1000L;

        var checkProducer = producerService.findById(producer1);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.producerService.delete(producer1);
        assertThat(testCase1.getName()).isEqualTo("Nintendo");

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.producerService.findById(producer1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Producer mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.producerService.delete(producer2));
    }
}
