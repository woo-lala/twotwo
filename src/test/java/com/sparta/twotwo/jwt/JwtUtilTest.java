package com.sparta.twotwo.jwt;

import com.sparta.twotwo.auth.jwt.JwtUtil;
import com.sparta.twotwo.auth.util.AuthorityUtil;
import com.sparta.twotwo.members.entity.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private Member mockMember;
    @Value("${jwt-master-email}")
    private String masterEmail;
    @Autowired
    private AuthorityUtil authorityUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        mockMember = Mockito.mock(Member.class);
        System.out.println("Master Email: " + masterEmail);
        when(mockMember.getMember_id()).thenReturn(1L);
        when(mockMember.getUsername()).thenReturn("testUser01");
        when(mockMember.getRoles()).thenReturn(authorityUtil.createRoles(masterEmail));
    }

    @Test
    void testCreateToken() {
        String token = jwtUtil.createToken(mockMember);
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));

        System.out.println("Generated Token: " + token);
    }
}