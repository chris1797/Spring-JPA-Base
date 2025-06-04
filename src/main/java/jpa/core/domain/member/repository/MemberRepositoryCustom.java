package jpa.core.domain.member.repository;

import jpa.core.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByName(String name);

    Page<Member> findPagingListByName(String keyword, Pageable pageable);

}
