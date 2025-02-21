package com.sparta.twotwo.members.entity;

import com.sparta.twotwo.common.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="p_members")
public class Member extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean is_public = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "p_member_roles")
    private Set<String> roles = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatusEnum memberStatus = MemberStatusEnum.ACTIVE;

    public Member(String username, String nickname, String email, String password, Set<String> roles, boolean is_public) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.is_public = is_public;
    }

    public void addRole(RolesEnum role) {
        this.roles.add(role.getAuthority());
    }
}
