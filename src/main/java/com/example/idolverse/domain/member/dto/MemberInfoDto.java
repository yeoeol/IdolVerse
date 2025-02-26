package com.example.idolverse.domain.member.dto;

import java.util.List;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.member.entity.enums.ProfileType;

public record MemberInfoDto(
	Long memberId,
	String email,
	String nickname,
	String profileName,
	ProfileType profileType,
	Boolean hasMembership,
	Boolean hasOfficialMark,
	List<CommunityMemberInfoDto> communityMemberInfoDtoList
) {
}
