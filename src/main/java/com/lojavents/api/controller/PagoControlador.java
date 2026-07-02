package com.lojavents.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagoControlador {

    @GetMapping("/")
    public String inicio() {
        return "Inicio jejejeje";
    }
}