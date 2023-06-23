package com.waff.gameverse_backend.datamodel;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class LoginResponse {
    private final String accessToken;
}
