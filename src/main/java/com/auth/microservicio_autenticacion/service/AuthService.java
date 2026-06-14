package com.auth.microservicio_autenticacion.service;

import com.auth.microservicio_autenticacion.client.UsuarioClient;
import com.auth.microservicio_autenticacion.dto.UsuarioResponseDTO;
import com.auth.microservicio_autenticacion.exception.UsuarioNotFoundException;
import com.auth.microservicio_autenticacion.security.JwtService;
import com.auth.microservicio_autenticacion.dto.AuthRequestDTO;
import com.auth.microservicio_autenticacion.dto.AuthResponseDTO;
import com.auth.microservicio_autenticacion.model.Auth;
import com.auth.microservicio_autenticacion.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder encoder;
    private final UsuarioClient usuarioClient;
    private final JwtService jwtService;

    private AuthResponseDTO mapToDTO(Auth auth, String token) {
        return AuthResponseDTO.builder()
                .idUsuario(auth.getIdUsuario())
                .nombreUsuario(auth.getNombreUsuario())
                .rol(auth.getRol())
                .token(token)
                .mensaje("Operación exitosa")
                .build();
    }

    public AuthResponseDTO registrar(AuthRequestDTO dto) {
        Auth auth = new Auth();
        auth.setNombreUsuario(dto.getNombreUsuario());
        auth.setContrasena(encoder.encode(dto.getContrasena()));
        auth.setCorreo(dto.getCorreo());
        auth.setRol(dto.getRol());

        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        usuario.setIdUsuario(auth.getIdUsuario());
        usuario.setNombreUsuario(auth.getNombreUsuario());
        usuario.setCorreo(auth.getCorreo());
        usuario.setRol(auth.getRol());
        usuario.setActivo(true);
        usuario.setEquipoId(null);

        usuarioClient.crear(usuario);
        return mapToDTO(authRepository.save(auth), null);
    }


    public AuthResponseDTO login(String nombreUsuario, String password) {
        Auth auth = authRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(password, auth.getContrasena())) {
            throw new RuntimeException("Credenciales incorrectas");
        }
        String token = jwtService.generarToken(auth);

        return mapToDTO(auth, token);
    }

    public AuthResponseDTO validarToken(String headerToken) {
        if (headerToken == null || headerToken.isBlank()) {
            throw new RuntimeException("Token no proporcionado");
        }

        String token = headerToken.startsWith("Bearer ") ? headerToken.substring(7) : headerToken;

        if (!jwtService.validarToken(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }

        try {
            String nombreUsuario = jwtService.extraerUsername(token);
            Auth auth = authRepository.findByNombreUsuario(nombreUsuario)
                    .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

            return mapToDTO(auth, token);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }



}
