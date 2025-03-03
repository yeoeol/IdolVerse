package com.example.idolverse.domain.community.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.idolverse.domain.community.entity.Community;

import lombok.Builder;

@Builder
public record CommunityInfoDto(
	Long communityId,
	String communityName,
	String urlPath,
	Long memberCount,
	String fandomName,
	String artistCode,
	LocalDateTime openDate
) {
	public static CommunityInfoDto from(Community community) {
		return CommunityInfoDto.builder()
			.communityId(community.getCommunityId())
			.communityName(community.getCommunityName())
			.urlPath(community.getUrlPath())
			.memberCount(Long.valueOf(community.getMembers() != null ? community.getMembers().size() : 0))
			.fandomName(community.getFandomName())
			.artistCode(community.getArtistCode())
			.openDate(community.getCreatedAt())
			.build();
	}
}
