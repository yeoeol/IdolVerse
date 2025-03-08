package com.example.idolverse.domain.account.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.account.dto.LoginRequestDto;
import com.example.idolverse.domain.account.dto.LoginResponseDto;
import com.example.idolverse.domain.account.dto.RefreshRequestDto;
import com.example.idolverse.domain.account.dto.RegisterRequestDto;
import com.example.idolverse.domain.account.dto.RegisterResponseDto;
import com.example.idolverse.domain.account.dto.TokenResponseDto;
import com.example.idolverse.domain.account.service.AccountService;
import com.example.idolverse.domain.account.service.RefreshTokenService;
import com.example.idolverse.global.security.jwt.JwtProperties;
import com.example.idolverse.global.security.jwt.JwtProvider;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;
	private final RefreshTokenService refreshTokenService;
	private final JwtProperties jwtProperties;

	@PostMapping("/register")
	public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto requestDto) {
		RegisterResponseDto responseDto = accountService.register(requestDto);
		return ResponseEntity.ok(responseDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(
		HttpServletResponse response,
		@RequestBody LoginRequestDto requestDto
	) {
		TokenResponseDto responseDto = accountService.login(requestDto);

		setAccessTokenHeader(response, responseDto.accessToken());
		setHttpOnlyCookie(response, responseDto.refreshToken());

		LoginResponseDto loginResponseDto = LoginResponseDto.from(responseDto.member());
		return ResponseEntity.ok(loginResponseDto);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDto> refresh(
		HttpServletResponse response,
		@RequestBody RefreshRequestDto requestDto
	) {
		TokenResponseDto responseDto = accountService.refresh(requestDto);

		setAccessTokenHeader(response, responseDto.accessToken());

		LoginResponseDto loginResponseDto = LoginResponseDto.from(responseDto.member());
		return ResponseEntity.ok(loginResponseDto);
	}

	private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(jwtProperties.getHeaderAuthorization(), "Bearer " + accessToken);
	}

	private void setHttpOnlyCookie(HttpServletResponse response, String refreshToken) {
		ResponseCookie refreshTokenCookie = ResponseCookie
			.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(jwtProperties.getRefreshToken().getExpiration())
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
	}
}
