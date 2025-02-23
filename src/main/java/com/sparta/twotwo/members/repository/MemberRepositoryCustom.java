package com.sparta.twotwo.members.repository;

import com.sparta.twotwo.members.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {
    Page<Member> findByRoleAndMemberStatus(String role, String status, boolean isDesc, Pageable pageable);

    Page<Member> findAllAndMemberStatus(String status, boolean isDesc, Pageable pageable);
}
