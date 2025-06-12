package jpa.core.service;

import jpa.core.api.member.request.MemberJoinRequest;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.team.entity.Team;
import jpa.core.domain.member.service.MemberService;
import jpa.core.domain.member.repository.MemberRepository;
import jpa.core.domain.team.repository.TeamRepository;
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
@ActiveProfiles("test")
public class TeamMemberTest {


    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("팀 생성 테스트")
    @Test
    @Rollback(false)
    void createTeam() {
        // given
        for (int i = 0; i < 20; i++) {
            Team team = new Team("Team-" + i);
            teamRepository.save(team);
        }

        // when

        // then
    }

    @DisplayName("팀과 회원 저장 및 조회테스트")
    @Test
    void test() {
        // given
        MemberJoinRequest request = new MemberJoinRequest("chris", null, "서울시", "관악구");
        Member joinedMember = memberService.join(request);

        // when
        Team team = new Team("D-PLUS-KIA");
        joinedMember.setTeam(team);
        Team savedTeam = teamRepository.save(team);

        // then
        joinedMember.setTeam(savedTeam);
        System.out.println("joinedMember = " + joinedMember);
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

    @DisplayName("N+1 문제 해결을 위한 팀 멤버 조회")
    @Test
    @Transactional
    void test4() {
        // given
        List<Member> members = memberRepository.findAll();

        for (Member member : members) {
            System.out.println("Team No :: " + member.getTeam());
        }
    }
}
