package com.auth.microservicio_autenticacion.client;

import com.auth.microservicio_autenticacion.dto.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuarios", path = "/api/usuarios")
public interface UsuarioClient {

    @PostMapping
    UsuarioResponseDTO crear(@RequestBody UsuarioResponseDTO usuario);

    @GetMapping("/{id}")
    UsuarioResponseDTO buscarPorId(@PathVariable Long id);

}
