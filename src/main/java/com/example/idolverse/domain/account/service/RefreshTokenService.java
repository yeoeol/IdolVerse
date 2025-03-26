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

	@Transactional
	public void saveRefreshToken(String email, String refreshToken) {
		long expiration = jwtProperties.getRefreshToken().getExpiration();
		redisService.save(email, refreshToken, expiration);
	}

	@Transactional
	public void deleteRefreshToken(String email) {
		redisService.delete(email);
	}

	@Transactional(readOnly = true)
	public String findRefreshToken(String email) {
		return redisService.find(email);
	}
}
