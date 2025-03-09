package com.example.idolverse.domain.account.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.account.dto.LoginRequestDto;
import com.example.idolverse.domain.account.dto.RefreshRequestDto;
import com.example.idolverse.domain.account.dto.RegisterRequestDto;
import com.example.idolverse.domain.account.dto.RegisterResponseDto;
import com.example.idolverse.domain.account.dto.TokenResponseDto;
import com.example.idolverse.domain.account.entity.RefreshToken;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.domain.member.service.MemberService;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;
import com.example.idolverse.global.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	private final MemberService memberService;

	public RegisterResponseDto register(RegisterRequestDto requestDto) {
		validateEmail(requestDto.email());
		String encodedPassword = passwordEncoder.encode(requestDto.password());
		Member member = requestDto.toEntity(encodedPassword);
		return RegisterResponseDto.from(memberRepository.save(member));
	}

	public TokenResponseDto login(LoginRequestDto requestDto) {
		Member member = memberRepository.findByEmail(requestDto.email())
			.filter(m -> passwordEncoder.matches(requestDto.password(), m.getPassword()))
			.orElseThrow(() -> new GeneralException(ErrorCode.LOGIN_FAILED));

		String accessToken = jwtProvider.generateAccessToken(member.getEmail());
		String refreshToken = jwtProvider.generateRefreshToken();

		refreshTokenService.saveRefreshToken(refreshToken, member.getMemberId());
		return TokenResponseDto.of(accessToken, refreshToken, member);
	}

	public TokenResponseDto refresh(RefreshRequestDto requestDto) {
		jwtProvider.validateToken(requestDto.refreshToken());

		RefreshToken refreshToken = refreshTokenService.findByRefreshToken(requestDto.refreshToken());
		Member member = memberService.findById(refreshToken.getMemberId());

		String accessToken = jwtProvider.generateAccessToken(member.getEmail());

		return TokenResponseDto.of(accessToken, null, member);
	}

	private void validateEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new GeneralException(ErrorCode.DUPLICATE_EMAIL);
		}
	}
}
