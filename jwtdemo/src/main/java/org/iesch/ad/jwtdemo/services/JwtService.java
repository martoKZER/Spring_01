package org.iesch.ad.jwtdemo.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    static String secret = "Estaba dormido pero me he espabilado porque hemos empezado a picar";
    static Key hmacKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    public String createJwt() {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder().claim("name", "Vladi")
                .claim("email", "vladisstanislao@iesch.org")
                .setSubject("Vladislaf Ivarov")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7L, ChronoUnit.DAYS)))
                .signWith(hmacKey).compact();
        return jwtToken;
    }

    public Jws parseJwt(String jwtString) {
        Jws<Claims> jwt = Jwts.parserBuilder().setSigningKey(hmacKey).build()
                .parseClaimsJws(jwtString);
        log.info(jwt.toString());

        return jwt;
    }


    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(jwt));
    }

    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(jwt).getBody();
    }

    public boolean validateToken(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private boolean extractExpiration(String jwt, Object getExpiration) {
        return extractClaims(jwt, Claims::getExpiration);
    }
}
