package com.sparta.twotwo.auth.util;

import com.sparta.twotwo.members.entity.RolesEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorityUtil {
    private final static String masterEmail = "master@email.com";
    private final static String managerEmail = "manager@email.com";
    private final static String ownerEmail = "owner@email.com";


    private final String CUSTOMER = RolesEnum.CUSTOMER.getAuthority();
    private final List<String> MASTER_STRING = List.of(RolesEnum.MASTER.getAuthority(), CUSTOMER);
    private final List<String> MANAGER_STRING = List.of(RolesEnum.MANAGER.getAuthority(), CUSTOMER);
    private final List<String> OWNER_STRING = List.of(RolesEnum.OWNER.getAuthority(), CUSTOMER);
    private final List<String> CUSTOMER_STRING = List.of(CUSTOMER);

    public List<String> createRoles(String email) {
        if(email.equals(masterEmail)) {
            return MASTER_STRING;
        } else if(email.equals(managerEmail)) {
            return MANAGER_STRING;
        } else if(email.equals(ownerEmail)) { // 테스트를 위한 owner 권한 설정 (이메일말고 다른 검증으로 권한 부여 예정)
            return OWNER_STRING;
        }
        return CUSTOMER_STRING;
    }

    public List<GrantedAuthority> createAuthorities(List<String> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
