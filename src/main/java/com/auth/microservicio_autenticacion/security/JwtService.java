package com.auth.microservicio_autenticacion.security;

import com.auth.microservicio_autenticacion.model.Auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String llaveSecreta;

    private static final long EXPIRATION_TIME = 86400000;

    public String generarToken(Auth user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", user.getRol());
        claims.put("idUsuario", user.getIdUsuario());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getNombreUsuario()) // Usamos username como subject
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    public String extraerRol(String token) {
        return (String) extraerClaims(token).get("rol");
    }

    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(
                llaveSecreta.getBytes()
        );
    }
}
