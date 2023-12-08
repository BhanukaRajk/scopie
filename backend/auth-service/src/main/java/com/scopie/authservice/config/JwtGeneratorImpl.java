package com.scopie.authservice.config;

import com.scopie.authservice.dto.LoginDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtGeneratorImpl implements JwtGenerator {

//    @Value("${jwt.secret}") // GET SECRET KEY FROM THE APPLICATION.PROPERTIES FILE
//    public String secret;

    @Override
    public Map<String, String> generate(LoginDTO user) { // MAP THE JWT TOKEN IN TO STRING

//        String jwtToken;
        String secret = "4c6272525a5a7a7042583943586b63476f694f4d6562776a455435335075424da";

        String jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", "[`CUSTOMER`, `ADMIN`]")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 HOUR
                .signWith(SignatureAlgorithm.HS256, secret).compact();

        Map<String, String> jwtTokenGen = new HashMap<>();

        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("tokenType", "Bearer");
        jwtTokenGen.put("expiresIn", "1000*60*60");
        jwtTokenGen.put("role", "CUSTOMER");

        return jwtTokenGen; // RETURN THE JWT TOKEN
    }
}
