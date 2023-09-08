package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.dto.RoleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role implements DataTransferObject<RoleDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "privileges_in_role", joinColumns = {@JoinColumn(name = "role_id")},
        inverseJoinColumns = {@JoinColumn(name = "privilege_id")})
    private Set<Privilege> privileges;


    @OneToMany(mappedBy="role")
    private Set<User> users;

    public Role(RoleDto roleDto) {
        this.name       = roleDto.getName();
        this.privileges = new HashSet<>(roleDto.getPrivileges().stream().map(Privilege::new).toList());
        this.users = Set.of();

    }

    @Override
    public RoleDto convertToDto() {
        return new RoleDto(this.id, this.name, this.privileges.stream().map(Privilege::convertToDto).toList());
    }
}
