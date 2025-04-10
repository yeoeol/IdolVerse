package com.example.idolverse.domain.account.controller;

import java.time.Duration;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
import com.example.idolverse.global.response.ApiResponseDto;
import com.example.idolverse.global.security.jwt.JwtProperties;
import com.example.idolverse.global.security.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Account-Controller", description = "회원가입 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

	private final AccountService accountService;
	private final JwtProperties jwtProperties;

	@Operation(summary = "회원가입")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원가입 성공"),
		@ApiResponse(responseCode = "409", description = "이미 존재하는 이메일")
	})
	@PostMapping("/register")
	public ApiResponseDto<RegisterResponseDto> register(@ParameterObject @RequestBody RegisterRequestDto requestDto) {
		RegisterResponseDto responseDto = accountService.register(requestDto);
		return ApiResponseDto.success(responseDto);
	}

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ApiResponseDto<LoginResponseDto> login(
		HttpServletResponse response,
		@RequestBody LoginRequestDto requestDto
	) {
		TokenResponseDto responseDto = accountService.login(requestDto);

		setAccessTokenHeader(response, responseDto.accessToken());
		setHttpOnlyCookie(response, responseDto.refreshToken());

		LoginResponseDto loginResponseDto = LoginResponseDto.from(responseDto.member());
		return ApiResponseDto.success(loginResponseDto);
	}

	@Operation(summary = "access 토큰 재발급")
	@PostMapping("/refresh")
	public ApiResponseDto<LoginResponseDto> refresh(
		HttpServletResponse response,
		@RequestBody RefreshRequestDto requestDto
	) {
		TokenResponseDto responseDto = accountService.refresh(requestDto);

		setAccessTokenHeader(response, responseDto.accessToken());

		LoginResponseDto loginResponseDto = LoginResponseDto.from(responseDto.member());
		return ApiResponseDto.success(loginResponseDto);
	}

	@Operation(summary = "로그아웃")
	@DeleteMapping("/logout")
	public void logout(@RequestHeader("Authorization") String bearerToken) {
		System.out.println("AccountController.logout");
		accountService.logout(bearerToken);
	}

	private void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(jwtProperties.getHeaderAuthorization(), "Bearer " + accessToken);
	}

	private void setHttpOnlyCookie(HttpServletResponse response, String refreshToken) {
		long maxAgeSeconds = Duration.ofMillis(jwtProperties.getRefreshToken().getExpiration()).getSeconds();

		ResponseCookie refreshTokenCookie = ResponseCookie
			.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/")
				.maxAge(maxAgeSeconds)
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
	}
}
