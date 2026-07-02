package com.lojavents.api.servicio;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.lojavents.api.dto.LoginDTO;
import com.lojavents.api.dto.RegistroUsuarioDTO;
import com.lojavents.api.dominio.Usuario;
import com.lojavents.api.seguridad.DatosTokenJWT;
import com.lojavents.api.seguridad.ServicioTokens;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServicio {

    private final AuthenticationManager authenticationManager;
    private final UsuarioServicio usuarioServicio;
    private final ServicioTokens servicioTokens;

    public DatosTokenJWT autenticar(LoginDTO login) {
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.correo(), login.contrasena()));
        UserDetails usuarioAutenticado = (UserDetails) autenticacion.getPrincipal();
        String token = servicioTokens.generarToken(usuarioAutenticado);
        return new DatosTokenJWT(token, "Bearer");
    }

    public Usuario registrar(RegistroUsuarioDTO datos) {
        return usuarioServicio.registrar(datos);
    }
}
