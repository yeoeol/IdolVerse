package com.example.idolverse.domain.member.dto;

import com.example.idolverse.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record MemberInfoDto(
	Long memberId,
	String email,
	String userKey,
	String nickname
) {
	public static MemberInfoDto from(Member member) {
		return MemberInfoDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.userKey(member.getUserKey())
			.nickname(member.getNickname())
			.build();
	}
}
