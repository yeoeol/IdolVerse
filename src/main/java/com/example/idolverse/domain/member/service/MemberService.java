package com.example.idolverse.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.member.dto.MemberInfoDto;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.repository.MemberRepository;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberInfoDto findByEmail(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));

		return MemberInfoDto.from(member);
	}
}
