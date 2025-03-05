package com.example.idolverse.domain.account.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.account.entity.RefreshToken;
import com.example.idolverse.domain.account.repository.RefreshTokenRepository;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public void saveRefreshToken(String token, Long memberId) {
		RefreshToken refreshToken = RefreshToken.of(token, memberId);
		refreshTokenRepository.save(refreshToken);
	}

	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteById(token);
	}

	@Transactional(readOnly = true)
	public RefreshToken findByRefreshToken(String token) {
		return refreshTokenRepository.findById(token)
			.orElseThrow(() -> new GeneralException(ErrorCode.INVALID_TOKEN));
	}
}
