package com.jpa.base.service;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import com.jpa.base.domain.Member;
import com.jpa.base.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
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

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberService.findOne(saveId));
    }

}