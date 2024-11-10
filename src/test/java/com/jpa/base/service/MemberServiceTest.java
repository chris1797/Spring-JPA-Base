package com.jpa.base.service;

import com.jpa.base.domain.Member;
import com.jpa.base.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원 가입")
    @Test
    void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("chris");

        /*
         * 실제로는 insert 쿼리가 실행되지 않는다. @Transactional 어노테이션이 붙어있기 때문에
         * 트랜잭션을 커밋하지 않고 롤백하기 때문이다.
         * 따라서, 실제로 insert 쿼리가 실행되는지 확인하려면 @Rollback(false)를 붙이거나 em.flush()를 호출해야 한다.
         */
        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberService.findOne(saveId));
    }

    @DisplayName("회원 중복 검사")
    @Test
    void validateDuplicateMember() throws IllegalStateException {
        // given
        Member member1 = new Member();
        member1.setName("chris");

        Member member2 = new Member();
        member2.setName("chris");

        // when

        // then
        try {
            memberService.join(member1);
            memberService.join(member2);
        } catch (Exception e) {
            return;
        }
        // 예외가 발생해야 한다.
        throw new IllegalStateException("예외가 발생해야 한다.");
    }

}