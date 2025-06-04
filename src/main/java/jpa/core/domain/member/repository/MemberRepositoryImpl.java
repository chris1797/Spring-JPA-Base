package jpa.core.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.member.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Page<Member> findPagingListByName(String keyword, Pageable pageable) {
        QMember member = QMember.member;

        List<Member> list = query.selectFrom(member)
                .where(member.name.contains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(query.select(member.count())
                .from(member)
                .where(member.name.contains(keyword))
                .fetchOne())
                .orElse(0L);

        return new PageImpl<>(list, pageable, total);
    }

}
