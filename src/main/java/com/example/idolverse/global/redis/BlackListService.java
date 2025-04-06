package com.example.idolverse.global.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;
import com.example.idolverse.global.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListService {

	private final RedisTemplate<String, String> redisTemplate;
	private final JwtProvider jwtProvider;

	public final static String LOGOUT = "logout";

	public void save(String accessToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		long remainingTime = getRemainingTime(accessToken);

		valueOperations.set(accessToken, LOGOUT, remainingTime, TimeUnit.MILLISECONDS);
	}

	public String find(String accessToken) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		return valueOperations.get(accessToken);
	}

	public void delete(String accessToken) {
		redisTemplate.delete(accessToken);
	}

	private long getRemainingTime(String accessToken) {
		long currentTime = System.currentTimeMillis();
		long expirationTime = jwtProvider.getExpirationTime(accessToken);
		return expirationTime-currentTime;
	}
}
