package com.example.idolverse.domain.account.dto;

import com.example.idolverse.domain.member.entity.Member;

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
