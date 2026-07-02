    package com.lojavents.api.servicio;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lojavents.api.dto.RegistroUsuarioDTO;
import com.lojavents.api.dominio.Usuario;
import com.lojavents.api.repositorio.UsuarioRepositorio;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicio implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrar(RegistroUsuarioDTO datos) {
        if (usuarioRepositorio.existsByCorreo(datos.correo())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
        Usuario usuario = Usuario.builder()
                .nombre(datos.nombre())
                .correo(datos.correo())
                .contrasena(passwordEncoder.encode(datos.contrasena()))
                .rol("ROLE_CLIENTE")
                .build();
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no registrado"));
        return new User(usuario.getCorreo(), usuario.getContrasena(),
                List.of(new SimpleGrantedAuthority(usuario.getRol())));
    }
}
