package com.auth.microservicio_autenticacion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private Long idUsuario;
    private String nombreUsuario;
    private String rol;
    private String correo;
    private String token;
    private String mensaje;
}
