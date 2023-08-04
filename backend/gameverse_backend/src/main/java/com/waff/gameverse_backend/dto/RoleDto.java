package com.waff.gameverse_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * DataTransferObject für Rollen, dieses wird nur genutzt,
 * wenn wir den User an das Frontend geben.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto implements Serializable {

  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  private List<PrivilegeDto> privileges;
}
