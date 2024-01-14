package com.ecommerce.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    private static final String SECRET_KEY = "6d54686235724d4c576f334c65634468704c744172676753647a47396a397955";

    public <T> T extractClaim(String jwsToken, Function<Claims, T> claimsResolver) {
       final Claims allClaims = extractAllClaims(jwsToken);
       return claimsResolver.apply(allClaims);
    }

    public Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJwt(jwtToken)
                .getBody();
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date extractExpirationDate(String jwsToken) {
        return extractClaim(jwsToken, Claims::getExpiration);
    }

    private boolean isTokenExpired(String jwsToken) {
        return extractExpirationDate(jwsToken).before(new Date());
    }

    public boolean isTokenValid(String jwsToken, UserDetails userDetails) {
        final String userName = extractUsername(jwsToken);
        return userName.equals(userDetails.getUsername()) &&
        !isTokenExpired(jwsToken);
    }
    public String extractUsername(String jwsToken) {
        return extractClaim(jwsToken, Claims::getSubject);
    }

}
