package com.jpa.base.service;

import com.jpa.base.domain.Member;
import com.jpa.base.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member) throws Exception {
        log.info("회원 가입 - name: {}", member.getName());
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        if (memberRepository.getCountByName(member.getName()) > 0)
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        log.info("전체 회원 조회");
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
