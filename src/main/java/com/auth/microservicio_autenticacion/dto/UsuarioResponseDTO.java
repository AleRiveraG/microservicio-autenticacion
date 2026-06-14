package com.auth.microservicio_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String correo;
    private String rol;
    private Boolean activo;
    private Long equipoId;
}
