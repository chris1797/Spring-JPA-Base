package jpa.core.common.converter;

import jpa.core.api.member.request.MemberJoinRequest;
import jpa.core.domain.delivery.entity.Address;
import jpa.core.domain.member.entity.Member;
import jpa.core.domain.team.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public static Member toEntity(MemberJoinRequest request, Team team) {
        Member member = new Member();
        member.setName(request.name());
        member.setTeam(team);
        member.setAddress(Address.of(request.address(), request.addressDetail()));
        return member;
    }
}
