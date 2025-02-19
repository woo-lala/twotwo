package com.sparta.twotwo.auth.jwt;

import com.sparta.twotwo.members.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Getter
    private final String secretKey = String.valueOf(key);


    public String createToken(Member member) {
        Date date = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("member_id", member.getMember_id());
        claims.put("username", member.getUsername());
        claims.put("roles",  member.getRoles());
        String subject = member.getUsername();
        long TOKEN_TIME = 60 * 60 * 1000L;

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> getClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
