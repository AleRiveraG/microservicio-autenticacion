package com.auth.microservicio_autenticacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "autenticacion")
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombreUsuario;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false, length = 255)
    private String correo;

    @Column(nullable = false, length = 100)
    private String rol;

}
