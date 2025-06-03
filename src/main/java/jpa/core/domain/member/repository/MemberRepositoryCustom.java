package jpa.core.domain.member.repository;

import jpa.core.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByName(String name);

}
