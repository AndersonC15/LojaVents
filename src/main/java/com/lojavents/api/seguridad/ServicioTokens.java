package com.lojavents.api.seguridad;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class ServicioTokens {

    private final Key key;
    private final long expiracionSegundos;

    public ServicioTokens(@Value("${app.security.jwt.secret}") String secret,
                          @Value("${app.security.jwt.expiration-seconds}") long expiracionSegundos) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiracionSegundos = expiracionSegundos;
    }

    public String generarToken(UserDetails userDetails) {
        Instant ahora = Instant.now();
        List<String> autoridades = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", autoridades)
                .setIssuedAt(Date.from(ahora))
                .setExpiration(Date.from(ahora.plusSeconds(expiracionSegundos)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean esValido(String token, UserDetails userDetails) {
        final String correo = obtenerCorreo(token);
        return correo.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    public String obtenerCorreo(String token) {
        return obtenerClaim(token, Claims::getSubject);
    }

    private boolean estaExpirado(String token) {
        return obtenerClaim(token, Claims::getExpiration).before(Date.from(Instant.now()));
    }

    private <T> T obtenerClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
