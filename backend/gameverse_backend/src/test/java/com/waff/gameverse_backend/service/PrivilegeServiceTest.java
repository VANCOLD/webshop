package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.PrivilegeDto;
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
 * Testclass für PrivilegeService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(PrivilegeService.class)
@ActiveProfiles("test")
public class PrivilegeServiceTest {
    
    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private RoleRepository roleRepository;
    

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die user Rolle
        Long privilegeId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long privilegeId2 = 1000L;

        var testCase1 = this.privilegeService.findById(privilegeId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.privilegeService.findById(privilegeId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.privilegeService.findAll();
        assertThat(testCase1.size()).isEqualTo(8);
    }

    @Test
    void findByNameTest() {

        // Das Privilege mit den Namen user existiert
        String privilege1 = "edit_users";
        // Das Privilege edit_kuche hingegen nicht!
        String privilege2 = "edit_kuche";

        // Hier erwarten wir das wir das Privilege zurück bekommen die wir suchen
        var testCase1 = this.privilegeService.findByName(privilege1);
        assertThat(testCase1.getName()).isEqualTo(privilege1);

        // Da es keine Privilege mit den Namen edit_kuche gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.privilegeService.findByName(privilege2));
    }

    @Test
    void saveTest() {
        String privilege1 = "edit_kekse";

        String privilege2 = "edit_users";


        var testCase1 = this.privilegeService.save(privilege1);
        assertThat(testCase1.getName()).isEqualTo(privilege1);

        assertThrows(IllegalArgumentException.class, () -> this.privilegeService.save(privilege2));
    }

    @Test
    void updateTest() {
        PrivilegeDto privilege = new PrivilegeDto("test");
        assertThrows(NoSuchElementException.class,() -> this.privilegeService.update(privilege));

        privilege.setId(1L);
        var updatedRole = this.privilegeService.update(privilege);
        assertThat(updatedRole.getName()).isEqualTo(privilege.getName());

        privilege.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.privilegeService.update(privilege));

    }

    @Test
    void deleteTest() {

        // Privilege view_profile
        Long privilege1 = 1L;

        // Nicht existierendes Privilege
        Long privilege2 = 1000L;

        var checkPrivilege = privilegeService.findById(privilege1);

        var rolesWithPrivileges = this.roleRepository.findAllByPrivileges(checkPrivilege);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.privilegeService.delete(privilege1);
        assertThat(testCase1.getName()).isEqualTo("view_profile");

        assertThat(rolesWithPrivileges).isNotEqualTo(this.roleRepository.findAllByPrivileges(checkPrivilege));

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.privilegeService.findById(privilege1));

        // Dieser Aufruf sollte einen Fehler werfen da es kein Privilege mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.privilegeService.delete(privilege2));
    }
}
