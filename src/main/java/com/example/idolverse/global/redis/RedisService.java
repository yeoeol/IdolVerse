package com.example.idolverse.global.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, String> redisTemplate;

	public void save(String key, String value, long expiration) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		valueOperations.set(key, value, expiration, TimeUnit.MILLISECONDS);
	}

	public String find(String key) {
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

		return valueOperations.get(key);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}
}
