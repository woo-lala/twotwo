package com.sparta.twotwo.members.repository;

import com.sparta.twotwo.members.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
