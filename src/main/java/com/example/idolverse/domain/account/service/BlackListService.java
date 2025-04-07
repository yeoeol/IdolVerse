package com.example.idolverse.domain.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;
import com.example.idolverse.global.redis.RedisService;
import com.example.idolverse.global.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListService {

	private final RedisService redisService;
	private final JwtProvider jwtProvider;

	public final String LOGOUT = "logout";

	@Transactional
	public void saveBlackList(String accessToken) {
		long remainingTime = getRemainingTime(accessToken);

		redisService.save(accessToken, LOGOUT, remainingTime);
	}

	@Transactional(readOnly = true)
	public String findBlackList(String accessToken) {
		String value = redisService.find(accessToken);
		if (LOGOUT.equals(value)) {
			throw new GeneralException(ErrorCode.INVALID_TOKEN);
		}

		return value;
	}

	@Transactional
	public void deleteBlackList(String accessToken) {
		redisService.delete(accessToken);
	}

	private long getRemainingTime(String accessToken) {
		long currentTime = System.currentTimeMillis();
		long expirationTime = jwtProvider.getExpirationTime(accessToken);
		return expirationTime-currentTime;
	}
}
