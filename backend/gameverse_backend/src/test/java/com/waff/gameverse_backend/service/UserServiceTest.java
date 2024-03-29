package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.dto.RoleDto;
import com.waff.gameverse_backend.dto.SimpleUserDto;
import com.waff.gameverse_backend.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import(UserService.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findByIdTest() {

        // Der erste Eintrag in data.sql ist der user "user"
        Long userId1 = 1L;

        // Dieser Eintrag existiert nicht und sollte einen Fehler werfen
        Long userId2 = 1000L;

        var testCase1 = this.userService.findById(userId1);
        assertNotNull(testCase1);

        // Wir erwarte das eine NoSuchElementException geworfen wird wenn wir eine falsche Id angeben
        assertThrows(NoSuchElementException.class, () -> this.userService.findById(userId2));
    }

    @Test
    void findAllTest() {
        var testCase1 = this.userService.findAll();
        assertThat(testCase1.size()).isEqualTo(2);
    }

    @Test
    void findByNameTest() {

        // Die Rolle mit den Namen user existiert
        String user1 = "user";
        // Die Rolle emperor hingegen nicht!
        String user2 = "emperor";

        // Hier erwarten wir das wir den User zurückbekommen den wir suchen
        var testCase1 = this.userService.findByUsername(user1);
        assertThat(testCase1.getUsername()).isEqualTo(user1);

        // Da es keine Rolle mit den Namen emperor gibt sollte es einen Fehler werfen!
        assertThrows(NoSuchElementException.class, () -> this.userService.findByUsername(user2));
    }

    @Test
    void saveTest() {

        SimpleUserDto user1 = new SimpleUserDto();
        user1.setUsername("guest");
        user1.setPassword("hallo");
        user1.setRole( new RoleDto("test", List.of(new PrivilegeDto("edit_uses"))));

        SimpleUserDto user2 = new SimpleUserDto();
        user2.setUsername("user");
        user2.setPassword("password");
        user2.setRole( new RoleDto("test", List.of(new PrivilegeDto("edit_uses"))));

        var testCase1 = this.userService.save(user1);
        assertThat(testCase1.getUsername()).isEqualTo(user1.getUsername());

        assertThrows(IllegalArgumentException.class, () -> this.userService.save(user2));
    }

    @Test
    void updateTest() {

        UserDto user = new UserDto();
        user.setId(1000L);
        user.setUsername("guest");
        user.setPassword("hallo");
        user.setRole( new RoleDto("test", List.of(new PrivilegeDto("edit_uses"))));
        assertThrows(NoSuchElementException.class,() -> this.userService.update(user));

        user.setId(1L);
        var updatedUser = this.userService.update(user);
        assertThat(updatedUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void deleteTest() {


        // User user
        Long user1 = 1L;

        // Nicht existierender user
        Long user2 = 1000L;


        // Lösche des Eintrages der Rolle user
        this.userService.delete(user1);

        // Da wir den Eintrag gelöscht haben sollten wir diesen auch nicht mehr finden!
        assertThrows(NoSuchElementException.class, () -> this.userService.findById(user1));

        // Dieser Aufruf sollte einen Fehler werfen da es keine User mit der Id 1000 gibt!
        assertThrows(NoSuchElementException.class, () -> this.userService.delete(user2));
    }
}
