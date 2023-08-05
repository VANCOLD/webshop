package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.PrivilegeDto;
import com.waff.gameverse_backend.model.Privilege;
import com.waff.gameverse_backend.repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class PrivilegeService {

    private final
    PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }


    public Privilege findById(Long id) {
        return this.privilegeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Privilege mit der gegeben Id existiert nicht"));
    }

    public Set<Privilege> findByIds(Iterable<Long> ids) {
        return new HashSet<>(this.privilegeRepository.findAllById(ids));
    }

    public Set<Privilege> findAll() {
        return new HashSet<>(this.privilegeRepository.findAll());
    }

    public Privilege findByName(String name) {
        return this.privilegeRepository.findByName(name).orElseThrow(() -> new NoSuchElementException("Privilege mit dem gegeben Namen existiert nicht!"));
    }


    public Privilege save(String name) {

        var toCheck = this.privilegeRepository.findByName(name);

        if(toCheck.isEmpty()) {

            Privilege toSave = new Privilege();
            toSave.setName(name);
            return this.privilegeRepository.save(toSave);

        } else {
            throw new IllegalArgumentException("Der angegebene Name wird schon von einem Privilege genutzt!");
        }
    }

    public Privilege update(PrivilegeDto privilegeDto) {

        var toUpdate = this.privilegeRepository.findById(privilegeDto.getId())
                        .orElseThrow(() -> new NoSuchElementException("Privilege mit der gegebenen Id existiert nicht!"));

        if(privilegeDto.getName().isEmpty()) { throw new IllegalArgumentException("Der Name des Privileges darf nicht leer sein!"); }

        toUpdate.setName(privilegeDto.getName());
        return this.privilegeRepository.save(toUpdate);

    }

    public Privilege delete(PrivilegeDto privilegeDto) {

        var toDelete = this.privilegeRepository.findById(privilegeDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Privilege mit der gegebenen Id existiert nicht!"));

        this.privilegeRepository.delete(toDelete);
        return toDelete;

    }

}