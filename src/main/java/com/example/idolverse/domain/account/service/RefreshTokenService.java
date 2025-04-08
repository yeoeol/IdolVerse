package com.example.idolverse.domain.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;
import com.example.idolverse.global.redis.RedisService;
import com.example.idolverse.global.security.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RedisService redisService;
	private final JwtProperties jwtProperties;

	private final String REFRESH_CACHE_NAME = "refreshToken";

	@Transactional
	public void saveRefreshToken(String email, String refreshToken) {
		long expiration = jwtProperties.getRefreshToken().getExpiration();

		redisService.save(getKey(email), refreshToken, expiration);
	}

	@Transactional
	public void deleteRefreshToken(String email) {
		redisService.delete(getKey(email));
	}

	@Transactional(readOnly = true)
	public String findRefreshToken(String email) {
		String refreshToken = redisService.find(getKey(email));
		if (refreshToken == null) {
			throw new GeneralException(ErrorCode.INVALID_TOKEN);
		}

		return refreshToken;
	}

	private String getKey(String email) {
		return REFRESH_CACHE_NAME + ":" + email;
	}
}
