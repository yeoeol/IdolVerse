package com.example.idolverse.domain.community.dto;

import com.example.idolverse.domain.community.entity.Community;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "커뮤니티 상세 정보 DTO")
@Builder
public record CommunityDetailDto(
	Long communityId,
	String communityName,
	String artistCode
) {
	public static CommunityDetailDto from(Community community) {
		return CommunityDetailDto.builder()
			.communityId(community.getCommunityId())
			.communityName(community.getCommunityName())
			.artistCode(community.getArtistCode())
			.build();
	}
}
