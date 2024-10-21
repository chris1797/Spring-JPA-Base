package com.jpa.base.repository;

import com.jpa.base.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

//    @Rollback(false)
    @Test
    @Transactional
    void save() {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);

        // then
        Member findMember = memberRepository.find(savedId);

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getUsername(), member.getUsername());

        // 같은 영속성 컨텍스트에서는 id 값이 같으면 같은 인스턴스로 관리되기 때문에 같은 인스턴스로 관리된다.
        assertEquals(findMember, member);
    }

}