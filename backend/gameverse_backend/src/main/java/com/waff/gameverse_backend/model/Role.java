package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.dto.RoleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The Role class represents a role within the application.
 * It serves as an entity for managing roles in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements DataTransferObject<RoleDto> {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    /**
     * The name of the role.
     */
    @Column(name = "name")
    private String name;

    /**
     * The list of privileges associated with this role.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "privileges_in_role", joinColumns = {@JoinColumn(name = "role_id")},
        inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
    private List<Privilege> privileges = new ArrayList<>();

    /**
     * The list of users who have this role.
     */
    @OneToMany(mappedBy="role")
    private List<User> users = new ArrayList<>();

    /**
     * Constructs a Role entity from a RoleDto.
     *
     * @param roleDto The RoleDto containing role information.
     */
    public Role(RoleDto roleDto) {
        this.name = roleDto.getName();
        this.privileges = roleDto.getPrivileges().stream().map(Privilege::new).toList();
        this.users = List.of();
    }

    /**
     * Converts this Role entity to a RoleDto.
     *
     * @return The corresponding RoleDto containing role information.
     */
    @Override
    public RoleDto convertToDto() {
        return new RoleDto(this.id, this.name, this.privileges.stream().map(Privilege::convertToDto).toList());
    }
}
