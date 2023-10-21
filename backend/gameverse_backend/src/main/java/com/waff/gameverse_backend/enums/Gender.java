package com.waff.gameverse_backend.enums;

import lombok.Getter;

@Getter
public enum Gender {

    Female("Female"), Male("Male"), Diverse("Diverse");

    private final String gender;

    Gender( String gender) {
        this.gender = gender;
    }
}
