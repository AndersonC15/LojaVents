package com.lojavents.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email(message = "Debe proporcionar un correo válido") String correo,
        @NotBlank(message = "La contraseña no puede quedar vacía") String contrasena
) {
}
