package com.example.idolverse.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "로그인 요청 DTO")
public record LoginRequestDto(
	String email,
	String password
) {
}
