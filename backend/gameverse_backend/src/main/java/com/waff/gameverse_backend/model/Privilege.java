package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.dto.PrivilegeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * The Privilege class represents a privilege or permission within the application.
 * It implements the GrantedAuthority interface and serves as an entity for managing privileges in the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "privileges")
public class Privilege implements GrantedAuthority, DataTransferObject<PrivilegeDto> {

    /**
     * The unique identifier for the privilege.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long id;

    /**
     * The name of the privilege.
     */
    @Column(name = "name")
    private String name;

    /**
     * The list of roles associated with this privilege.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "privileges_in_role", joinColumns = {@JoinColumn(name = "privilege_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    /**
     * Constructs a Privilege entity from a PrivilegeDto.
     *
     * @param privilegeDto The PrivilegeDto containing privilege information.
     */
    public Privilege(PrivilegeDto privilegeDto) {
        this.name = privilegeDto.getName();
    }

    /**
     * Returns the authority of this privilege, which is its name.
     *
     * @return The name of the privilege as its authority.
     */
    @Override
    public String getAuthority() {
        return this.name;
    }

    /**
     * Converts this Privilege entity to a PrivilegeDto.
     *
     * @return The corresponding PrivilegeDto containing privilege information.
     */
    @Override
    public PrivilegeDto convertToDto() {
        return new PrivilegeDto(this.id, this.name);
    }
}
