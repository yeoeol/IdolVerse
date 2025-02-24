package com.example.idolverse.domain.account.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.idolverse.domain.account.dto.RegisterRequestDto;
import com.example.idolverse.domain.account.dto.RegisterResponseDto;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public RegisterResponseDto register(RegisterRequestDto requestDto) {
		validateEmail(requestDto.email());
		String encodedPassword = passwordEncoder.encode(requestDto.password());
		Member member = requestDto.toEntity(encodedPassword);
		return RegisterResponseDto.from(memberRepository.save(member));
	}


	private void validateEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new GeneralException(ErrorCode.DUPLICATE_EMAIL);
		}
	}
}
