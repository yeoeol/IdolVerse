package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record RegisterResponseDto(
	Long memberId,
	String email,
	String nickname
) {
	public static RegisterResponseDto from(Member member) {
		return RegisterResponseDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.nickname(member.getNickname())
			.build();
	}
}
