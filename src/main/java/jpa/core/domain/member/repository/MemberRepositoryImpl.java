package jpa.core.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public List<Member> findByName(String name) {
        QMember member = QMember.member;

        return query.selectFrom(member)
                .where(member.name.eq(name))
                .fetch();
    }

}
