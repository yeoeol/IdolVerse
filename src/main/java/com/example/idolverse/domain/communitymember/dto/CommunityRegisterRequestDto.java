package com.example.idolverse.domain.communitymember.dto;

public record CommunityRegisterRequestDto(
	Long memberId,
	Long communityId,
	String profileName
) {
}
