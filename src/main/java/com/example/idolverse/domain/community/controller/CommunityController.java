package com.example.idolverse.domain.community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.community.dto.CommunityCreateRequestDto;
import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.community.service.CommunityService;
import com.example.idolverse.global.response.ApiResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Community-Controller", description = "커뮤니티 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

	private final CommunityService communityService;

	@Operation(summary = "커뮤니티 생성 (관리자 권한)")
	@PostMapping("/create")
	public ApiResponseDto<CommunityInfoDto> communityCreate(@RequestBody CommunityCreateRequestDto requestDto) {
		CommunityInfoDto communityInfoDto = communityService.communityCreate(requestDto);
		return ApiResponseDto.success(communityInfoDto);
	}
}
