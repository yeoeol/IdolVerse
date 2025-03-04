package com.example.idolverse.domain.communitymember.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityRegisterRequestDto;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communityMember")
public class CommunityMemberController {

	private final CommunityMemberService communityMemberService;

	@PostMapping("/register")
	public ResponseEntity<CommunityMemberInfoDto> register(@RequestBody CommunityRegisterRequestDto requestDto) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.register(requestDto);
		return ResponseEntity.ok(communityMemberInfoDto);
	}
}
