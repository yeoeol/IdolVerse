package com.example.idolverse.domain.communitymember.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberUpdateRequestDto;
import com.example.idolverse.domain.communitymember.dto.CommunityRegisterRequestDto;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;

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

	@PatchMapping("/my-page")
	public ResponseEntity<CommunityMemberInfoDto> getMyPage(
		@ModelAttribute CommunityMemberUpdateRequestDto requestDto,
		@RequestParam("file") MultipartFile file,
		@AuthenticationPrincipal CustomMemberDetails memberDetails
	) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.updateInfo(requestDto, file, memberDetails.getMemberId());
		return ResponseEntity.ok(communityMemberInfoDto);
	}
}
