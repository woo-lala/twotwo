package com.sparta.twotwo.auth.jwt;

import com.sparta.twotwo.members.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Getter
    private final String secretKey = String.valueOf(key);

    public static final Logger logger = LoggerFactory.getLogger("Jwt 관련 로그");

    public String createToken(Member member) {
        Date date = new Date();
        Map<String, Object> claims = new HashMap<>();
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

//    public void addJwtCookie(String token, HttpServletResponse response) {
//        try {
//            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
//            Cookie cookie = new Cookie("Authorization", "Bearer " + token);
//
//            cookie.setPath("/");
//            response.addCookie(cookie);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }
//
//    public String substringToken(String tokenValue) {
//        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith("Bearer ")) {
//            return tokenValue.substring(7);
//        }
//        logger.error("Not Found Token");
//        throw new NullPointerException("Not Found Token");
//    }



}
