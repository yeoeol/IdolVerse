package com.example.idolverse.domain.community.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;

public record CommunityInfoDto(
	Long communityId,
	String communityName,
	String urlPath,
	Long memberCount,
	String fandomName,
	String artistCode,
	LocalDateTime updatedAt
) {
}
