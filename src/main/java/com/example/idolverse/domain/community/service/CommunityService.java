package com.example.idolverse.domain.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.community.dto.CommunityCreateRequestDto;
import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.repository.CommunityRepository;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

	private final CommunityRepository communityRepository;

	@Transactional
	public CommunityInfoDto communityCreate(CommunityCreateRequestDto requestDto) {
		Community community = requestDto.toEntity(requestDto);
		Community savedCommunity = communityRepository.save(community);
		return CommunityInfoDto.from(savedCommunity);
	}

	public Community findByUrlPath(String urlPath) {
		return communityRepository.findByUrlPath(urlPath)
			.orElseThrow(() -> new GeneralException(ErrorCode.COMMUNITY_NOT_FOUND));
	}

	public Community findById(Long communityId) {
		return communityRepository.findById(communityId)
			.orElseThrow(() -> new GeneralException(ErrorCode.COMMUNITY_NOT_FOUND));
	}
}
