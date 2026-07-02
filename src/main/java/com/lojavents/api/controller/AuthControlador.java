package com.lojavents.api.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojavents.api.dto.LoginDTO;
import com.lojavents.api.dto.RegistroUsuarioDTO;
import com.lojavents.api.servicio.AuthServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final AuthServicio authServicio;

    @PostMapping("/registro")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroUsuarioDTO datos) {
        authServicio.registrar(datos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado. Ahora puedes iniciar sesión y usar el token de acceso.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authServicio.autenticar(loginDTO));
    }
}
