package com.lojavents.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControlador {

    @GetMapping("/usuarios")
    public String inicio() {
        return "Backend LojaVents funcionando correctamente";
    }
}