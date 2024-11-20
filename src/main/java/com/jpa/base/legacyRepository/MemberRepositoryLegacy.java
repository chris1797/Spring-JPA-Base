package com.jpa.base.legacyRepository;

import com.jpa.base.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryLegacy {

    /**
     * @PersistenceContext : JPA 표준 스펙에 의해 EntityManager를 주입받을 수 있다.
     * 스프링이 EntityManager를 만들어서 주입해준다.
     */
//    @PersistenceContext
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public int getCountByName(String name) {
        return em.createQuery("select count(m) from Member m where m.name = :name", Long.class)
                .setParameter("name", name)
                .getSingleResult().intValue();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
