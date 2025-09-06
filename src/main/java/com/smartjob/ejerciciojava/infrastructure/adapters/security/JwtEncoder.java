package com.smartjob.ejerciciojava.infrastructure.adapters.security;

import com.smartjob.ejerciciojava.application.ports.output.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtEncoder implements TokenProvider {

    @Override
    public String createJwt(String subject, String issuer, long expirationMillis) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public static Claims decodeJwt(String jwtToken, Key signingKey) {
        //llave de ejemplo, solo para el ejercicio
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken);
        return jwsClaims.getBody();
    }
}
