package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.AddressDto;
import com.waff.gameverse_backend.model.Address;
import com.waff.gameverse_backend.model.Producer;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.AddressRepository;
import com.waff.gameverse_backend.repository.ProducerRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    private final ProducerRepository producerRepository;

    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository,
                          ProducerRepository producerRepository, UserRepository userRepository) {
        this.addressRepository  = addressRepository;
        this.producerRepository = producerRepository;
        this.userRepository     = userRepository;
    }

    /**
     * Find a address by its ID.
     *
     * @param id The ID of the address to find.
     * @return The found address.
     * @throws NoSuchElementException If no address with the given ID exists.
     */
    public Address findById(Long id) {
        return this.addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Address with the given ID does not exist"));
    }

    /**
     * Find all addresss.
     *
     * @return A list of all addresss.
     */
    public List<Address> findAll() {
        return this.addressRepository.findAll();
    }

    /**
     * Save a new address with the given name.
     *
     * @param addressDto The address to store.
     * @return The saved address.
     * @throws IllegalArgumentException If a address with the same name already exists.
     */
    public Address save(AddressDto addressDto) {

        if (!this.exists(addressDto)) {
            Address toSave = new Address(addressDto);
            return this.addressRepository.save(toSave);
        } else {
            throw new IllegalArgumentException("Angegebene Adresse existiert bereits!");
        }
    }

    public boolean exists(AddressDto address) {
        return addressRepository.existsByStreetAndCityAndPostalCodeAndCountry(address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry());
    }

    /**
     * Update a address with the information from the provided AddressDto.
     *
     * @param addressDto The AddressDto containing updated address information.
     * @return The updated address.
     * @throws NoSuchElementException  If the address with the given ID does not exist.
     * @throws IllegalArgumentException If the address name is empty.
     */
    public Address update(AddressDto addressDto) {

        var toUpdate = this.addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Address with the given ID does not exist"));

        if (addressDto.getStreet().isEmpty()) {
            throw new IllegalArgumentException("The name of the address cannot be empty");
        }

        toUpdate.setStreet(addressDto.getStreet());
        toUpdate.setCity(addressDto.getCity());
        toUpdate.setPostalCode(addressDto.getPostalCode());
        toUpdate.setCountry(addressDto.getCountry());

        return this.addressRepository.save(toUpdate);
    }

    /**
     * Delete a address with the given ID and update associated roles.
     *
     * @param id The ID of the address to delete.
     * @return The deleted address.
     * @throws NoSuchElementException If the address with the given ID does not exist.
     */
    public Address delete(Long id) {

        var toDelete = this.addressRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Address with the given ID does not exist"));

        var users = this.userRepository.findAllByAddress(toDelete);
        var producers = this.producerRepository.findAllByAddress(toDelete);

        for (User user : users) {
            user.setAddress(null);
            this.userRepository.save(user);
        }

        for (Producer producer : producers) {
            producer.setAddress(null);
            this.producerRepository.save(producer);
        }

        toDelete.setUsers(null);
        toDelete.setProducers(null);
        this.addressRepository.delete(toDelete);
        return toDelete;
    }

    public Address findByAddress(AddressDto address) {
        return addressRepository.findByStreetAndCityAndPostalCodeAndCountry(address.getStreet(), address.getCity(), address.getPostalCode(), address.getCountry());
    }


}
