package jpa.core.api.member.request;

import jpa.core.domain.member.entity.Member;

public record MemberJoinRequest(
        String name,
        Long teamId,
        String address,
        String addressDetail
) {
}
