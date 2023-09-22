package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testclass für RoleService Service. Benutzt die Daten des data.sql!
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(RoleService.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist die user Rolle
        Long roleId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long roleId2 = 1000L;

        var testCase1 = this.roleService.findById(roleId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.roleService.findById(roleId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.roleService.findAll();
        assertThat(testCase1.size()).isEqualTo(3);
    }

    @Test
    void findByNameTest() {

        // Die Rolle mit den Namen user existiert
        String role1 = "user";
        // Die Rolle emperor hingegen nicht!
        String role2 = "emperor";

        // Hier erwarten wir das wir die Rolle zurück bekommen die wir suchen
        var testCase1 = this.roleService.findByName(role1);
        assertThat(testCase1.getName()).isEqualTo(role1);

        // Da es keine Rolle mit den Namen emperor gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.roleService.findByName(role2));
    }

    @Test
    void saveTest() {

        String role1 = "guest";

        String role2 = "user";

        var testCase1 = this.roleService.save(role1);
        assertThat(testCase1.getName()).isEqualTo(role1);

        assertThrows(IllegalArgumentException.class, () -> this.roleService.save(role2));
    }

    @Test
    void updateTest() {
        RoleDto role = new RoleDto(0L,"test", List.of(new PrivilegeDto("edit_users")));
        assertThrows(NoSuchElementException.class,() -> this.roleService.update(role));

        role.setId(1L);
        var updatedRole = this.roleService.update(role);
        assertThat(updatedRole.getName()).isEqualTo(role.getName());

        role.setName("");
        assertThrows(IllegalArgumentException.class, () -> this.roleService.update(role));

    }

    @Test
    void deleteTest() {


        // Rolle user
        Long role1 = 1L;

        // Nicht existierende Rolle
        Long role2 = 1000L;

        var checkRole = roleService.findById(role1);
        System.out.println(checkRole.getId());

        var usersWithRole = this.userRepository.findAllByRole(checkRole);

        // Lösche des Eintrages der Rolle user
        var testCase1 = this.roleService.delete(role1);
        assertThat(testCase1.getName()).isEqualTo("user");
        System.out.println(checkRole.getId());

        assertThat(usersWithRole).isNotEqualTo(this.userRepository.findAllByRole(checkRole));

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.roleService.findById(role1));

        // Dieser Aufruf sollte einen Fehler werfen da es keine Rolle mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.roleService.delete(role2));
    }
}
