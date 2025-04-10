package com.example.idolverse.domain.account.dto;

import java.util.UUID;

import com.example.idolverse.domain.member.entity.Member;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "회원가입 요청 DTO")
public record RegisterRequestDto(
	@Schema(description = "회원 이메일")
	String email,
	@Schema(description = "회원 비밀번호")
	String password
) {
	public Member toEntity(String encodedPassword) {
		return Member.builder()
			.email(email)
			.userKey(UUID.randomUUID().toString())
			.password(encodedPassword)
			.build();
	}
}
