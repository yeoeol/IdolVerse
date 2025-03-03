package com.example.idolverse.domain.communitymember.service;

import org.springframework.stereotype.Service;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.communitymember.repository.CommunityMemberRepository;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {

	private final CommunityMemberRepository communityMemberRepository;

	public boolean existsMemberInCommunity(Community community, Member member) {
		if (communityMemberRepository.existsByCommunityAndMember(community, member)) {
			return true;
		}
		throw new GeneralException(ErrorCode.COMMUNITY_MEMBER_NOT_REGISTER);
	}
}
