package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "회원가입 응답 DTO")
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
