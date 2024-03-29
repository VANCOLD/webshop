package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.dto.SimpleUserDto;
import com.waff.gameverse_backend.enums.Gender;
import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.dto.UserDto;
import com.waff.gameverse_backend.utils.SimpleDataTransferObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The User class represents a user within the application.
 * It implements the UserDetails interface and serves as an entity for managing user information in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, DataTransferObject<UserDto>, SimpleDataTransferObject<SimpleUserDto> {

    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The username of the user (unique).
     */
    @Column(name = "username", unique = true)
    private String username;

    /**
     * The password of the user.
     */
    @Column(name = "password")
    private String password;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    
    /**
     * The role associated with this user.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;


    @OneToMany(mappedBy="user")
    private List<Order> orders = new ArrayList<>();


    /**
     * Constructs a User entity from a SimpleUserDto.
     *
     * @param simpleUserDto The SimpleUserDto containing user information.
     */
    public User(SimpleUserDto simpleUserDto) {
        this.username = simpleUserDto.getUsername();
        this.password = simpleUserDto.getPassword();
        this.role     = simpleUserDto.getRole() == null ? new Role() : new Role(simpleUserDto.getRole());
    }

    public User(UserDto userDto) {
        this.username = userDto.getUsername();
        this.password = userDto.getPassword();
        this.role = userDto.getRole() == null ? new Role() : new Role(userDto.getRole());
        this.firstname = userDto.getFirstname();
        this.lastname = userDto.getLastname();
        this.gender = Gender.valueOf(userDto.getGender());
        this.email  = userDto.getEmail();
        this.address = userDto.getAddress() == null ? new Address() : new Address(userDto.getAddress());
    }

    /**
     * Returns the authorities (privileges) associated with this user's role.
     *
     * @return The collection of GrantedAuthority objects representing the user's privileges.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getPrivileges();
    }

    /**
     * Returns the password of the user.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username of the user.
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return true if the account is not expired; otherwise, false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * @return true if the account is not locked; otherwise, false.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are not expired.
     *
     * @return true if the credentials are not expired; otherwise, false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user is enabled; otherwise, false.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Converts this User entity to a UserDto.
     *
     * @return The corresponding UserDto containing user information.
     */
    @Override
    public UserDto convertToDto() {
        return new UserDto(id, this.username, this.password, this.gender.getGender(),this.firstname, this.lastname, this.email, this.address.convertToDto(), this.role.convertToDto());
    }

    @Override
    public SimpleUserDto convertToSimpleDto() {
        return new SimpleUserDto(id, username, password, role.convertToDto());
    }
}
