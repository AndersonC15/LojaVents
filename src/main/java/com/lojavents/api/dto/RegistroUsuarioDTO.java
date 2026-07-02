package com.lojavents.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistroUsuarioDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @Email(message = "El correo debe ser válido") String correo,
        @NotBlank(message = "La contraseña es obligatoria") String contrasena
) {
}
