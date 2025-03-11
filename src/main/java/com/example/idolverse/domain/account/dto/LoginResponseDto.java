package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "로그인 성공 응답 DTO")
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
