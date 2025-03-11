package com.example.idolverse.domain.member.dto;

import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(title = "통합 회원 정보 DTO")
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
