package com.example.idolverse.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.member.dto.MemberInfoDto;
import com.example.idolverse.domain.member.service.MemberService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Member-Controller", description = "멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "통합 회원 정보 조회")
	@GetMapping("/me")
	public ResponseEntity<MemberInfoDto> getMemberInfo(@AuthenticationPrincipal CustomMemberDetails member) {
		MemberInfoDto memberInfoDto = memberService.findByEmail(member.getUsername());
		return ResponseEntity.ok(memberInfoDto);
	}
}
