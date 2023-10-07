package com.waff.gameverse_backend.enums;

import lombok.Getter;

@Getter
public enum Gender {

    FEMALE("Female"), MALE("Male"), DIVERSE("Diverse");

    private final String gender;

    Gender( String gender) {
        this.gender = gender;
    }
}
