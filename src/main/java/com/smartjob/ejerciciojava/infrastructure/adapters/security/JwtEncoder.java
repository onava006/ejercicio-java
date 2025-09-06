package com.smartjob.ejerciciojava.application.utils;

import com.smartjob.ejerciciojava.application.ports.output.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtEncoder implements TokenGenerator {

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
                .signWith(key) // Sign with your secret key
                .compact();
    }
}
