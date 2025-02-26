package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record TokenResponseDto(
	String accessToken,
	String refreshToken,
	Member member
) {

	public static TokenResponseDto of(String accessToken, String refreshToken, Member member) {
		return TokenResponseDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.member(member)
			.build();
	}
}
