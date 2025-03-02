package com.example.idolverse.domain.account.controller;

import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.account.dto.RefreshRequestDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RedisController {

	private final RedisTemplate<String, Object> redisTemplate;

	@PostMapping("/redisTest")
	public Object addRedisKey(@RequestBody RefreshRequestDto requestDto) {
		String prefix = UUID.randomUUID().toString();
		String key = prefix + "refreshToken:";
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		valueOperations.set(key, requestDto.refreshToken());
		return valueOperations.get(key);
	}
}
