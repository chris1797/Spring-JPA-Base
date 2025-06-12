package jpa.core.service;

import jpa.core.api.member.request.MemberJoinRequest;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.member.service.MemberService;
import jpa.core.domain.member.repository.MemberRepository;
import jpa.core.domain.team.entity.Team;
import jpa.core.domain.team.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    static Team team;

    @BeforeEach
    void setUp() {
        // 테스트용 팀 생성
        team = new Team();
        team.setName("Test Team");

        teamRepository.save(team);
        // 팀을 데이터베이스에 저장
//        memberService.join(new MemberJoinRequest(team.getName(), team.getDescription()));
    }

    @DisplayName("회원 가입 테스트")
    @Test
    @Rollback(false)
    void join() {
        // given
        for (int i = 1; i < 30; i++) {
            MemberJoinRequest request = new MemberJoinRequest(
                    "chris_" + i,
                    team.getId(),
                    "서울시",
                    "강남구"
            );
//            member.setName("chris_" + i);
            memberService.join(request);
        }

        /*
         * 실제로는 insert 쿼리가 실행되지 않는다. @Transactional 어노테이션이 붙어있기 때문에 트랜잭션을 커밋하지 않고 롤백하기 때문이다.
         * 따라서, 실제로 insert 쿼리가 실행되는지 확인하려면 @Rollback(false)를 붙이거나 em.flush()를 호출해야 한다.
         */
        // when

        List<Member> memberList = memberService.fetchAllMembers();

        // then
        assertEquals(30, memberList.size());
    }

    @DisplayName("회원가입 시 이름 중복 여부 검사")
    @Test
    void validateDuplicateMember() throws IllegalStateException {
        // given
        MemberJoinRequest request1 = new MemberJoinRequest(
                "chris_duplicate",
                team.getId(),
                "서울시",
                "강남구"
        );

        MemberJoinRequest request2 = new MemberJoinRequest(
                "chris_duplicate",
                team.getId(),
                "서울시",
                "강남구"
        );

        // then
        try {
            memberService.join(request1);
            memberService.join(request2);
        } catch (Exception e) {
            return;
        }
        // 예외가 발생해야 한다.
        throw new IllegalStateException("예외가 발생해야 한다.");
    }

    @DisplayName("회원 전체 조회")
    @Test
    void findMembers() {
        List<Member> members = memberRepository.findAll();

        assertEquals(10, members.size());
    }

    @DisplayName("회원 페이징 조회")
    @Test
    void findMembersWithPaging() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        String keyword = "chris_1";

        // when
        List<Member> members = memberRepository.findPagingListByName(keyword, pageable).getContent();

        boolean isContainsKeyword = members.stream()
                .allMatch(member -> member.getName().contains(keyword));

        // then
        assertEquals(members.size(), pageable.getPageSize());
        assertTrue(isContainsKeyword);
    }


    @DisplayName("이름으로 회원 조회")
    @Test
    void findByName() {
        // given
        String findName = "chris_";

        List<Member> list = memberRepository.findByName(findName);

        // when

        // then
        assertNotEquals(0, list.size());
        assertEquals(findName, list.getFirst().getName());
    }

}