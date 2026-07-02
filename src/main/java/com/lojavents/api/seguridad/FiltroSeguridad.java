package com.lojavents.api.seguridad;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lojavents.api.servicio.UsuarioServicio;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FiltroSeguridad extends OncePerRequestFilter {

    private final ServicioTokens servicioTokens;
    private final UsuarioServicio usuarioServicio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String cabecera = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            String token = cabecera.substring(7);
            try {
                String correo = servicioTokens.obtenerCorreo(token);
                if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails usuario = usuarioServicio.loadUserByUsername(correo);
                    if (servicioTokens.esValido(token, usuario)) {
                        UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                                usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(autenticacion);
                    }
                }
            } catch (JwtException ex) {
                // No hacemos nada aquí para dejar pasar la solicitud sin autenticación
            }
        }
        filterChain.doFilter(request, response);
    }
}
