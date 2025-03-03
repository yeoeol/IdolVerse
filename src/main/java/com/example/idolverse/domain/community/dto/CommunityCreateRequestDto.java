package com.example.idolverse.domain.community.dto;

import com.example.idolverse.domain.community.entity.Community;

public record CommunityCreateRequestDto(
	String communityName,
	String urlPath,
	String fandomName,
	String artistCode
) {
	public Community toEntity(CommunityCreateRequestDto requestDto) {
		return Community.builder()
			.communityName(requestDto.communityName())
			.urlPath(requestDto.urlPath())
			.memberCount(0L)
			.fandomName(requestDto.fandomName())
			.artistCode(requestDto.artistCode())
			.build();
	}
}
