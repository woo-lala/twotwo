package com.sparta.twotwo.auth.util;

import com.sparta.twotwo.members.entity.RolesEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorityUtil {
    @Value("${jwt-master-email}")
    private String masterEmail;
    @Value("${jwt-manager-email}")
    private String managerEmail;
    private final static String ownerEmail = "owner@email.com";


    private final String CUSTOMER = RolesEnum.CUSTOMER.getAuthority();
    private final String MASTER = RolesEnum.MASTER.getAuthority();
    private final String MANAGER = RolesEnum.MANAGER.getAuthority();
    private final String OWNER = RolesEnum.OWNER.getAuthority();

    public Set<String> createRoles(String email) {
        Set<String> roles = new HashSet<>();
        roles.add(CUSTOMER);
        if(email.equals(masterEmail)) {
            roles.add(MASTER);
            return roles;
        } else if(email.equals(managerEmail)) {
            roles.add(MANAGER);
            return roles;
        }
        return roles;
    }


    public Set<GrantedAuthority> createAuthorities(Set<String> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
