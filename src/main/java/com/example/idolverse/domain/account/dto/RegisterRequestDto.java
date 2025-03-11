package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "회원가입 요청 DTO")
public record RegisterRequestDto(
	String email,
	String password
) {
	public Member toEntity(String encodedPassword) {
		return Member.builder()
			.email(email)
			.password(encodedPassword)
			.build();
	}
}
