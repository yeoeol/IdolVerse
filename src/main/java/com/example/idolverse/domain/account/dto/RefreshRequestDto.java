package com.example.idolverse.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "access 토큰 재발급 요청 DTO", description = "refresh 토큰을 이용해서 access 토큰 재발급")
public record RefreshRequestDto(
	String refreshToken
) {
}
