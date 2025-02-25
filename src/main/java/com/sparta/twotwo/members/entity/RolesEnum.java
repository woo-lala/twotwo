package com.sparta.twotwo.members.entity;

import lombok.Getter;

@Getter
public enum RolesEnum {
    CUSTOMER("CUSTOMER"),
    OWNER("OWNER"),
    MANAGER("MANAGER"),
    MASTER("MASTER");

    private final String authority;

    RolesEnum(String authority) {
        this.authority = authority;
    }

}
