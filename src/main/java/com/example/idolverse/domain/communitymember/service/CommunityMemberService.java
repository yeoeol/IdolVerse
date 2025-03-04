package com.example.idolverse.domain.communitymember.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.service.CommunityService;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityRegisterRequestDto;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.communitymember.repository.CommunityMemberRepository;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.service.MemberService;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {

	private final CommunityMemberRepository communityMemberRepository;
	private final CommunityService communityService;
	private final MemberService memberService;

	public boolean existsMemberInCommunity(Community community, Member member) {
		if (communityMemberRepository.existsByCommunityAndMember(community, member)) {
			return true;
		}
		throw new GeneralException(ErrorCode.COMMUNITY_MEMBER_NOT_REGISTER);
	}

	@Transactional
	public CommunityMemberInfoDto register(CommunityRegisterRequestDto requestDto) {
		Community community = communityService.findById(requestDto.communityId());
		Member member = memberService.findById(requestDto.memberId());

		CommunityMember communityMember = requestDto.toEntity(community, member, requestDto.profileName());
		CommunityMember savedCommunityMember = communityMemberRepository.save(communityMember);
		return CommunityMemberInfoDto.from(savedCommunityMember);
	}
}
