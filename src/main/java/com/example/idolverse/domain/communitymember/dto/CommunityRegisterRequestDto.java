package com.example.idolverse.domain.communitymember.dto;

import java.time.LocalDateTime;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "커뮤니티 가입 요청 DTO")
public record CommunityRegisterRequestDto(
	Long memberId,
	String profileName
) {
	public CommunityMember toEntity(Community community, Member member, String profileName) {
		return CommunityMember.builder()
			.community(community)
			.member(member)
			.profileName(profileName)
			.joinedAt(LocalDateTime.now())
			.followingCount(0L)
			.followerCount(0L)
			.build();
	}
}
