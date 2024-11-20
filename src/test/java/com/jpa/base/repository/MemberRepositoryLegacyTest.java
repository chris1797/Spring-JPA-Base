package com.jpa.base.repository;

import com.jpa.base.domain.Member;
import com.jpa.base.legacyRepository.MemberRepositoryLegacy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Rollback(false)
@Transactional
@SpringBootTest
class MemberRepositoryLegacyTest {

    @Autowired
    MemberRepositoryLegacy memberRepositoryLegacy;

//    @Rollback(false)
    @Test
    @DisplayName("Member 저장 테스트")
    void saveTest() {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long savedId = memberRepositoryLegacy.save(member);

        // then
        Member findMember = memberRepositoryLegacy.findOne(savedId);

        assertEquals(findMember.getId(), member.getId());
        assertEquals(findMember.getName(), member.getName());

        // 같은 영속성 컨텍스트에서는 id 값이 같으면 같은 인스턴스로 관리되기 때문에 같은 인스턴스로 관리된다.
        assertEquals(findMember, member);
    }

    @Test
    @DisplayName("Member 수정 테스트")
    void updateTest() {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long savedId = memberRepositoryLegacy.save(member);

        // then
        Member findMember = memberRepositoryLegacy.findOne(savedId);
        findMember.setName("memberB");

        // 변경 감지(Dirty Checking)으로 인해 update 쿼리가 자동으로 날아간다.
        assertEquals(findMember.getName(), "memberB");
    }

    @Test
    @DisplayName("Member 수정 실패 테스트")
    void updateFailTest() {
        // given
        Member member = new Member();
        member.setName("memberA");

        // when
        Long savedId = memberRepositoryLegacy.save(member);

        // then
        Member findMember = memberRepositoryLegacy.findOne(savedId);
        System.out.println("Before findMember = " + findMember.getName());
        // 변경 감지(Dirty Checking)으로 인해 update 쿼리가 자동으로 날아간다.
        findMember.setName("memberB");

        Member findMember2 = memberRepositoryLegacy.findOne(savedId);
        System.out.println("After findMember = " + findMember2.getName());

        // 변경 감지(Dirty Checking)으로 인해 update 쿼리가 자동으로 날아간다.
        assertNotEquals(findMember.getName(), "memberA");
    }

}