package com.example.idolverse.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.member.dto.MemberInfoDto;
import com.example.idolverse.domain.member.service.MemberService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/me")
	public ResponseEntity<MemberInfoDto> getMemberInfo(@AuthenticationPrincipal CustomMemberDetails member) {
		MemberInfoDto memberInfoDto = memberService.findByEmail(member.getUsername());
		return ResponseEntity.ok(memberInfoDto);
	}
}
