package com.sparta.twotwo.members.repository;

import com.sparta.twotwo.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByUsername(String username);
}
