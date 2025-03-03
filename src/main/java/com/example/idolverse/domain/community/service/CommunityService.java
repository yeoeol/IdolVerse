package com.example.idolverse.domain.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.community.dto.CommunityCreateRequestDto;
import com.example.idolverse.domain.community.dto.CommunityInfoDto;
import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.repository.CommunityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {

	private final CommunityRepository communityRepository;

	@Transactional
	public CommunityInfoDto communityCreate(CommunityCreateRequestDto requestDto) {
		Community community = requestDto.toEntity(requestDto);
		Community savedCommunity = communityRepository.save(community);
		return CommunityInfoDto.from(savedCommunity);
	}
}
