package com.waff.gameverse_backend.dto;

import com.waff.gameverse_backend.model.Producer;
import com.waff.gameverse_backend.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDto {

    private Long id;

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String postalCode;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String country;

    private List<SimpleUserDto> users;

    private List<ProducerDto> producers;

}
