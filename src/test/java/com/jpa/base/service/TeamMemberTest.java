package com.jpa.base.service;

import com.jpa.base.domain.Member;
import com.jpa.base.domain.Team;
import com.jpa.base.repository.MemberRepository;
import com.jpa.base.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TeamMemberTest {


    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("팀과 회원 저장 및 조회테스트")
    @Test
    void test() {
        // given
        Member member = new Member();
        member.setName("chris");
        Member joinedMember = memberService.join(member);

        // when
        Team team = new Team("D-PLUS-KIA");
        member.setTeam(team);
        Team savedTeam = teamRepository.save(team);

        // then
        joinedMember.setTeam(savedTeam);
        System.out.println("joinedMember = " + joinedMember.toString());
    }

    @DisplayName("팀 가입 후 회원의 팀 조회 테스트")
    @Test
    @Rollback(false)
    void test2() {
        // given
        Member member = memberService.fetchMember(2L);

        // when
        Team team = teamRepository.findById(2L).orElse(null);

        // then
        member.setTeam(team);
    }

    @DisplayName("팀 멤버 조회")
    @Test
    @Transactional
    void test3() {
        // given
        Team team = teamRepository.findById(2L).orElse(null);

        // when
        assert team != null;
        List<Member> members = team.getMembers();

        assertThat(!members.isEmpty()).isTrue();
    }
}
