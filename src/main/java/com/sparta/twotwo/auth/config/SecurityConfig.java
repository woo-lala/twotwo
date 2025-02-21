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
                    /*
                    권한 설정 설명 : 기본적으로는 CUSTOMER 권한을 가지고 있고, 위와 같이 각 권한에 맞게 메소드와 URL 경로를 지정하고 권한 설정
                                 권한이 다중일때는 hasAnyRole, 단일일때는 hasRole 로 설정
                     */
                    //member
                    .requestMatchers("/api/members/signup").permitAll()
                    .requestMatchers("/api/members/login").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/members").hasAnyRole("MASTER", "MANAGER")
                    .requestMatchers(HttpMethod.GET, "/api/members/**").hasRole("CUSTOMER")
                    .requestMatchers(HttpMethod.PATCH, "/api/members/grant/**").hasAnyRole("MASTER", "MANAGER")
                    .requestMatchers(HttpMethod.PATCH, "/api/members/**").hasAnyRole("MASTER", "CUSTOMER")
                    .requestMatchers(HttpMethod.DELETE, "/api/members/**").hasAnyRole("MASTER", "CUSTOMER")
                    //store

                    //products
                    .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole("OWNER", "MANAGER")
                    .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("OWNER", "MANAGER")
                    .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("OWNER", "MANAGER")


                    //orders

                    //review
                    .requestMatchers(HttpMethod.POST, "/api/reviews").hasAnyRole("MASTER", "MANAGER", "CUSTOMER")
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
