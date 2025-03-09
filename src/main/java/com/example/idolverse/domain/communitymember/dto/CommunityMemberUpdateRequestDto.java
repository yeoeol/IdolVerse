package com.example.idolverse.domain.communitymember.dto;

public record CommunityMemberUpdateRequestDto(
	Long communityMemberId,
	String profileName,
	String profileComment
) {
}
