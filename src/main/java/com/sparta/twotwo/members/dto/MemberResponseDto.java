package com.sparta.twotwo.members.dto;

import com.sparta.twotwo.members.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long member_id;
    private String username;
    private String nickname;
    private String email;
    private Set<String> roles;
    private String memberStatus;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public MemberResponseDto(Member member) {
        this.member_id = member.getMember_id();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.roles = member.getRoles();
        this.memberStatus = member.getMemberStatus().toString();
        this.created_at = member.getCreatedAt();
        this.updated_at = member.getUpdatedAt();
    }
}
