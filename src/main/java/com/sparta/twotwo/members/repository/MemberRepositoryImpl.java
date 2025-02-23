package com.sparta.twotwo.members.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.twotwo.members.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.twotwo.members.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Member> findByRoleAndMemberStatus(String role, String status, boolean isDesc, Pageable pageable) {
        JPAQuery<Member> query = jpaQueryFactory
                .selectFrom(member)
                .where(member.roles.contains(role),
                        statusEq(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Member> members = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(members, pageable, total);
    }

    @Override
    public Page<Member> findAllAndMemberStatus(String status, boolean isDesc, Pageable pageable) {
        JPAQuery<Member> query = jpaQueryFactory
                .selectFrom(member)
                .where(
                        statusEq(status)
                )
                .orderBy(getOrderSpecifier(isDesc))// orderBy 여부
                .offset(pageable.getOffset()) //페이지네이션 적용
                .limit(pageable.getPageSize()); //페이지 크기

        List<Member> members = query.fetch(); //조회 결과
        long total = query.fetchCount(); // 조회 개수

        return new PageImpl<>(members, pageable, total);
    }

    private BooleanExpression statusEq(String status) {//status 값이 없으면 무시
        return (status != null && !status.isEmpty()) ? member.memberStatus.stringValue().eq(status) : null;
    }

    private OrderSpecifier<?> getOrderSpecifier(boolean isDesc) {
        return isDesc ? member.createdAt.desc() : member.createdAt.asc();
    }
}
