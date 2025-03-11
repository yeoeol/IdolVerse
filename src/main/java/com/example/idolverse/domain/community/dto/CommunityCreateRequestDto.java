package com.example.idolverse.domain.community.dto;

import com.example.idolverse.domain.community.entity.Community;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "커뮤니티 가입 요청 DTO (관리자 권한)")
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
