package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import lombok.Builder;

@Builder
public record LoginResponseDto(
	Long memberId,
	String nickname
) {
	public static LoginResponseDto from(Member member) {
		return LoginResponseDto.builder()
			.memberId(member.getMemberId())
			.nickname(member.getNickname())
			.build();
	}
}
