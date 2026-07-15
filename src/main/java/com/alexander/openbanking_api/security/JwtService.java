package com.alexander.openbanking_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.function.Function;

// handles generating and validating jwt tokens
@Service
public class JwtService {

    // secret key from application.yml
    @Value("${jwt.secret}")
    private String secret;

    // token expiration time
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // generate a jwt token
    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()

                // sets the username (email)
                .subject(userDetails.getUsername())

                // token creation time
                .issuedAt(new Date())

                // token expiration
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))

                // sign the token
                .signWith(getSignInKey())

                .compact();
    }

    // extract email from token
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    // validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

    // check if token has expired
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());

    }

    // get expiration date
    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);

    }

    // extract any claim
    public <T> T extractClaim(String token,
                              Function<Claims, T> resolver) {

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);

    }

    // extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSignInKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

    // convert secret into encryption key
    private SecretKey getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);

    }

}