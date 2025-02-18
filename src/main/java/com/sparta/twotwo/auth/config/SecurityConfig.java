package com.sparta.twotwo.auth.config;

import com.sparta.twotwo.auth.jwt.JwtUtil;
import com.sparta.twotwo.auth.jwt.filter.JwtAuthenticationFilter;
import com.sparta.twotwo.auth.jwt.filter.JwtAuthorizationFilter;
import com.sparta.twotwo.auth.util.AuthorityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthorityUtil authorityUtil;

    public SecurityConfig(JwtUtil jwtUtil, AuthorityUtil authorityUtil) {
        this.jwtUtil = jwtUtil;
        this.authorityUtil = authorityUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizationRequests) -> {
            authorizationRequests
                    .requestMatchers("/api/members/signup").permitAll()
                    .requestMatchers("/api/members/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/members/**").hasRole("MASTER")
                    .anyRequest().authenticated();
        })
                .formLogin(AbstractHttpConfigurer::disable)
                .with(new CustomFilterConfig(), CustomFilterConfig::getClass);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil);
            JwtAuthorizationFilter authorizationFilter = new JwtAuthorizationFilter(jwtUtil, authorityUtil);
            authenticationFilter.setFilterProcessesUrl("/api/members/login");

            builder
                    .addFilter(authenticationFilter)
                    .addFilterAfter(authorizationFilter, AuthenticationFilter.class);
        }
    }
}
