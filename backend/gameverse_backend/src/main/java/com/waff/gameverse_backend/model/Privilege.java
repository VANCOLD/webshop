package com.waff.gameverse_backend.model;

import com.waff.gameverse_backend.utils.DataTransferObject;
import com.waff.gameverse_backend.dto.PrivilegeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "privileges")
public class Privilege implements GrantedAuthority, DataTransferObject<PrivilegeDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "privilege_id")
    private Long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "privileges_in_role", joinColumns = {@JoinColumn(name = "privilege_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;


    public Privilege(PrivilegeDto privilegeDto) {
        this.name = privilegeDto.getName();
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    @Override
    public PrivilegeDto convertToDto() {
        return new PrivilegeDto(this.name);
    }
}