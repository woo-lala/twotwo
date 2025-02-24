package com.sparta.twotwo.auth.jwt;

import com.sparta.twotwo.members.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


//    private SecretKey key;
//    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Getter
    @Value("${jwt-secret-key}")
    private String secretKey;

//    @Getter
//    private final String secretKey = String.valueOf(key);

    @Value("${expire-token-time}")
    int expireTokenTime;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }



    public String createToken(Member member, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        Date date = new Date();
        Map<String, Object> claims = new HashMap<>();
        claims.put("member_id", member.getMember_id());
        claims.put("username", member.getUsername());
        claims.put("roles",  member.getRoles());
        String subject = member.getUsername();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(date.getTime() + expireTokenTime))
                .setIssuedAt(date)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> getClaims(String token, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

}
