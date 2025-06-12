package jpa.core.domain.member.service;

import jpa.core.api.member.request.MemberJoinRequest;
import jpa.core.common.converter.MemberConverter;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.member.repository.MemberRepository;
import jpa.core.domain.team.entity.Team;
import jpa.core.domain.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    // 회원 가입
    @Transactional
    public Member join(MemberJoinRequest request) {
        log.info("회원 가입 - name: {}", request.name());
        validateDuplicateMember(request.name()); // 중복 회원 검증

        Team team = teamRepository.findById(request.teamId()).orElse(null);
        Member member = MemberConverter.toEntity(request, team);
        memberRepository.save(member);
        return member;
    }


    private void validateDuplicateMember(String name) {
        if (memberRepository.countByName(name) > 0)
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    // 전체 회원 조회
    public List<Member> fetchAllMembers() {
        log.info("전체 회원 조회");
        return memberRepository.findAll();
    }

    public Member fetchMember(Long memberId) {
        return memberRepository.findById(memberId).orElse(null);
    }
}
