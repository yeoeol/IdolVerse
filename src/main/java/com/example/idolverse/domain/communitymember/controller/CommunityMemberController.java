package com.example.idolverse.domain.communitymember.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberUpdateRequestDto;
import com.example.idolverse.domain.communitymember.dto.CommunityRegisterRequestDto;
import com.example.idolverse.domain.communitymember.service.CommunityMemberService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;
import com.example.idolverse.global.response.ApiResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "CommunityMember-Controller", description = "특정 커뮤니티 관련 API")
@RestController
@RequiredArgsConstructor
public class CommunityMemberController {

	private final CommunityMemberService communityMemberService;

	@Operation(summary = "특정 커뮤니티 가입")
	@PostMapping("/{urlPath}/register")
	public ApiResponseDto<CommunityMemberInfoDto> register(
		@PathVariable String urlPath,
		@RequestBody CommunityRegisterRequestDto requestDto
	) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.register(urlPath, requestDto);
		return ApiResponseDto.success(communityMemberInfoDto);
	}

	@Operation(summary = "특정 커뮤니티 내 회원 정보 조회")
	@GetMapping("/{urlPath}/profile/{id}")
	public ApiResponseDto<CommunityMemberInfoDto> getMyPage(
		@PathVariable String urlPath,
		@PathVariable("id") Long communityMemberId,
		@AuthenticationPrincipal CustomMemberDetails memberDetails
	) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.getInfo(
			communityMemberId, memberDetails.getMemberId()
		);
		return ApiResponseDto.success(communityMemberInfoDto);
	}

	@Operation(summary = "특정 커뮤니티 내 회원 정보 수정")
	@PatchMapping("/{urlPath}/profile/{id}")
	public ApiResponseDto<CommunityMemberInfoDto> updateMyPage(
		@PathVariable String urlPath,
		@PathVariable("id") Long communityMemberId,
		@ModelAttribute CommunityMemberUpdateRequestDto requestDto,
		@RequestParam(value = "file", required = false) MultipartFile file,
		@AuthenticationPrincipal CustomMemberDetails memberDetails
	) {
		CommunityMemberInfoDto communityMemberInfoDto = communityMemberService.updateInfo(
			communityMemberId, requestDto, file, memberDetails.getMemberId()
		);
		return ApiResponseDto.success(communityMemberInfoDto);
	}
}
