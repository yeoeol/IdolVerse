package com.example.idolverse.domain.community.dto;

import java.util.List;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;

public record CommunityInfoDto(
	Long communityId,
	String communityName,
	Long memberCount,
	String fandomName,
	String artistCode,
	List<CommunityMemberInfoDto> members
) {
}
