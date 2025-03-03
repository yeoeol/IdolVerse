package com.example.idolverse.domain.community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.idolverse.domain.community.dto.CommunityCreateRequestDto;
import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.community.service.CommunityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

	private final CommunityService communityService;

	@PostMapping("/create")
	public ResponseEntity<CommunityInfoDto> communityCreate(@RequestBody CommunityCreateRequestDto requestDto) {
		CommunityInfoDto communityInfoDto = communityService.communityCreate(requestDto);
		return ResponseEntity.ok(communityInfoDto);
	}
}
