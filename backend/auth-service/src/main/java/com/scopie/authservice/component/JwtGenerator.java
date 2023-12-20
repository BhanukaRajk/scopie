package com.scopie.authservice.component;

import com.scopie.authservice.dto.LoginDTO;
import com.scopie.authservice.entity.Customer;
import com.scopie.authservice.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
//import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtGenerator {

    @Autowired
    private AuthService authService;

//    @Value("${application.security.jwt.secret}")
//    private String secret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        Customer customer = authService.findByUsername(username);
        return (username.equals(customer.getEmail()) && !isTokenExpired(token));
    }


    public String generateToken(LoginDTO user){
        Map<String, Object> claims=new HashMap<>();
        return createToken(claims, user);
    }

    public String createToken(Map<String, Object> claims, LoginDTO user) { // MAP THE JWT TOKEN IN TO STRING
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 HOUR
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();

        return jwtToken; // RETURN THE JWT TOKEN
    }

    public Key getSignKey() {
        String secret = "4c6272525a5a7a7042583943586b63476f694f4d6562776a455435335075424da";
        byte[] keyBytes = Decoders.BASE64URL.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
