package com.example.apidemo.jwt;

import com.example.apidemo.security.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${com.example.jwt.secret}")
    private String JWT_SECRET;
    @Value("${com.example.jwt.expiration}")
    private String JWT_EXPIRATION;

    //tao jwt tu thong tin cua user
    public String generateToken(CustomUserDetails customUserDetails) {
        Date now = new Date();
        Date dateExprired = new Date(now + JWT_EXPIRATION);
        //tao chuoi jwt tu username
        return Jwts.builder().setSubject(customUserDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(dateExprired)
                .signWith(SignatureAlgorithm.ES512, JWT_SECRET).compact();
    }

    //Lay user tu chuoi token
    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        //tra lai thong tin username
        return claims.getSubject();
    }

    //validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex) {
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex) {
            log.error("Expired JWT Token");
        }catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT Token");
        }catch (IllegalArgumentException ex) {
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
