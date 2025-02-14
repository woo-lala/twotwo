package com.sparta.twotwo.members.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="members")
public class Member {
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
    private List<String> roles = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MemberStatusEnum memberStatus =  MemberStatusEnum.ACTIVE;

    public Member(String username, String nickname, String email, String password, List<String> roles, boolean is_public) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.is_public = is_public;
    }
}
