package com.example.idolverse.global.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	private final static String REFRESH_CACHE_NAME = "refreshToken";

	public void save(String email, String refreshToken, long expiration) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		String key = getKey(email);
		valueOperations.set(key, refreshToken, expiration, TimeUnit.MILLISECONDS);
	}

	public String find(String email) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		return Optional.ofNullable(valueOperations.get(getKey(email)))
			.orElseThrow(() -> new GeneralException(ErrorCode.INVALID_TOKEN));
	}

	private static String getKey(String email) {
		return REFRESH_CACHE_NAME + ":" + email;
	}

	public void delete(String email) {
		String key = getKey(email);
		redisTemplate.delete(key);
	}
}
