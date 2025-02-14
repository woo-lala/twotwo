package com.sparta.twotwo.auth.util;

import com.sparta.twotwo.members.entity.RolesEnum;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AuthorityUtil {
    private final static String masterEmail = "master@email.com";
    private final static String managerEmail = "manager@email.com";

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
        }
        return CUSTOMER_STRING;
    }
}
