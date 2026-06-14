package com.auth.microservicio_autenticacion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}
