package jpa.core.domain.member.repository;

import jpa.core.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    int countByName(String name);
}
