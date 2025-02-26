package com.example.idolverse.domain.communitymember.dto;

import java.time.LocalDateTime;

import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.member.dto.MemberInfoDto;

public record CommunityMemberInfoDto(
	Long id,
	CommunityInfoDto communityInfoDto,
	MemberInfoDto memberInfoDto,
	LocalDateTime joinedAt
) {
}
