package com.example.idolverse.domain.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.account.dto.RegisterRequestDto;
import com.example.idolverse.domain.account.dto.RegisterResponseDto;
import com.example.idolverse.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto requestDto) {
		RegisterResponseDto responseDto = accountService.register(requestDto);
		return ResponseEntity.ok(responseDto);
	}


}
