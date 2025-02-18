package com.sparta.twotwo.auth.jwt.filter;

import com.sparta.twotwo.auth.jwt.JwtUtil;
import com.sparta.twotwo.auth.util.AuthorityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthorityUtil authorityUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthorityUtil authorityUtil) {
        this.jwtUtil = jwtUtil;
        this.authorityUtil = authorityUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthentication(claims);
        }  catch (Exception e) {
            log.error(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String  header = request.getHeader("Authorization");

        return header == null || !header.startsWith("Bearer ");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");

        return jwtUtil.getClaims(token).getBody();
    }

    private void setAuthentication(Map<String, Object> claims) {
        String username = claims.get("username").toString();
        List<GrantedAuthority> authorities = authorityUtil.createAuthorities((List<String>) claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
