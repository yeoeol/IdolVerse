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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "CommunityMember-Controller", description = "특정 커뮤니티와 멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communityMember")
public class CommunityMemberController {

	private final CommunityMemberService communityMemberService;

	@Operation(summary = "특정 커뮤니티 가입")
	@PostMapping("/register")
	public ResponseEntity<CommunityMemberInfoDto> register(@RequestBody CommunityRegisterRequestDto requestDto) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.register(requestDto);
		return ResponseEntity.ok(communityMemberInfoDto);
	}

	@Operation(summary = "특정 커뮤니티 내 회원 정보 수정")
	@PatchMapping("/my-page")
	public ResponseEntity<CommunityMemberInfoDto> updateMyPage(
		@ModelAttribute CommunityMemberUpdateRequestDto requestDto,
		@RequestParam("file") MultipartFile file,
		@AuthenticationPrincipal CustomMemberDetails memberDetails
	) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.updateInfo(requestDto, file, memberDetails.getMemberId());
		return ResponseEntity.ok(communityMemberInfoDto);
	}
}
