package com.example.idolverse.domain.account.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken", timeToLive = 30) // 604800
public class RefreshToken {

	@Id
	private String refreshToken;

	private Long memberId;

	public static RefreshToken of(String token, Long memberId) {
		return RefreshToken.builder()
			.refreshToken(token)
			.memberId(memberId)
			.build();
	}
}
