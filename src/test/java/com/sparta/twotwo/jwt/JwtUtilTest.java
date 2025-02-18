package com.sparta.twotwo.jwt;

import com.sparta.twotwo.auth.jwt.JwtUtil;
import com.sparta.twotwo.auth.util.AuthorityUtil;
import com.sparta.twotwo.members.entity.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private Member mockMember;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        AuthorityUtil authorityUtil = new AuthorityUtil();
        mockMember = Mockito.mock(Member.class);
        List<String> roles = authorityUtil.createRoles("master@email.com");


        when(mockMember.getUsername()).thenReturn("testUser");
        when(mockMember.getRoles()).thenReturn(roles);
    }

    @Test
    void testCreateToken() {
        String token = jwtUtil.createToken(mockMember);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));

        System.out.println("Generated Token: " + token);
    }

    @Test
    void testAddJwtCookie() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        jwtUtil.addJwtCookie("testToken", response);


        verify(response, times(1)).addCookie(any(Cookie.class));
    }
}