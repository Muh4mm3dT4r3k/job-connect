package com.mohamed.jobconnectv2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secretKey}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;


    public Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String generateToken( UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .signWith(getSignKey())
                .setSubject(userDetails.getUsername())
                .setClaims(extraClaims)
                .claim("role", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }

    public String extractUsername(String jwt) {
        var claims = extractAllClaims(jwt);
        return claimResolver(claims, Claims::getSubject);
    }

    public Date extractExpiration(String jwt) {
        var claims = extractAllClaims(jwt);
        return claimResolver(claims, Claims::getExpiration);
    }

    public boolean isExpiredToken(String jwt) {
        return extractExpiration(jwt)
                .before(new Date(System.currentTimeMillis()));
    }

    public boolean isValidToken(String jwt,UserDetails userDetails) {
        return extractUsername(jwt)
                .equals(userDetails
                        .getUsername())
                && !isExpiredToken(jwt);
    }

    private <T> T claimResolver(Claims claims, Function<Claims, T> resolver){
        return resolver.apply(claims);
    }


    private Key getSignKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
