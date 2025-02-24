package com.sparta.twotwo.members.service;

import com.sparta.twotwo.auth.util.AuthorityUtil;
import com.sparta.twotwo.auth.util.SecurityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.dto.MemberRequestDto;
import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.entity.MemberStatusEnum;
import com.sparta.twotwo.members.entity.RolesEnum;
import com.sparta.twotwo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityUtil authorityUtil;
    private final PasswordEncoder passwordEncoder;


    public void createMember(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        boolean is_public = requestDto.is_public();
        Set<String> roles = authorityUtil.createRoles(email);

        existEmail(email);
        existUsername(username);

        Member member = new Member(username, nickname, email, password, roles, is_public);

        Long defaultCreateBy = 0L;
        member.setCreatedBy(defaultCreateBy);

        memberRepository.save(member);
    }

    public Page<Member> getMembers(String role, String status, int size, int page, boolean isDesc) {
        Pageable pageable = PageRequest.of(page - 1, size);

        if(role == null) {
           return memberRepository.findAllAndMemberStatus(status, isDesc, pageable);
        }
        return memberRepository.findByRoleAndMemberStatus(role, status, isDesc, pageable);
    }

    public Member getMember(Long memberId) {
        Member member = findMember(memberId);
        if(!member.is_public() || member.getMemberStatus().equals(MemberStatusEnum.DELETE)) {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }
        return member;
    }

    @Transactional
    public Member updateMember(Long memberId, MemberRequestDto requestDto) {
        Member authMember = findMember(authenticateMember());
        if(authMember.getRoles().contains("MASTER") || authMember.getMember_id().equals(memberId)) {
            Member member = findMember(memberId);

            Optional.ofNullable(requestDto.getNickname()).ifPresent(member::setNickname);
            Optional.ofNullable(requestDto.getPassword()).ifPresent(member::setPassword);

            return member;
        } else {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Transactional
    public Member addMemberAuth(Long memberId, RolesEnum role) {
            Member member = findMember(memberId);
            if(member.getRoles().contains("OWNER")) {
                throw new TwotwoApplicationException(ErrorCode.MEMBER_GRANT_EXIST);
            } else {
                member.addRole(role);
                return member;
            }
    }

    public void removeMemberAuth(Long memberId) {
        Member member = findMember(memberId);
        if(!member.getRoles().contains("OWNER")) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND);
        } else {
            member.getRoles().remove("OWNER");
        }
    }

    public void deleteMember(Long memberId) {
        Member member = findMember(memberId);
        Member authMember = findMember(authenticateMember());
        if(authMember.getRoles().contains("MASTER") || authMember.getMember_id().equals(memberId)) {
            member.setMemberStatus(MemberStatusEnum.DELETE);
            member.setDeletedBy(authenticateMember());
            member.setDeletedAt(LocalDateTime.now());

            System.out.println("token id: " + SecurityUtil.getMemberIdFromSecurityContext());

            memberRepository.save(member);
        } else {
            throw new TwotwoApplicationException(ErrorCode.UNAUTHORIZED);
        }
    }

    public void existEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_EMAIL_EXIST);
        }
    }

    public void existUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_USERNAME_EXIST);
        }
    }

    public Member findMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(() ->
                new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Long authenticateMember() {
        return SecurityUtil.getMemberIdFromSecurityContext();
    }
}
