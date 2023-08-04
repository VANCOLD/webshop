package com.waff.gameverse_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto implements Serializable {

  private UserDto user;

  private String jwt;
}
