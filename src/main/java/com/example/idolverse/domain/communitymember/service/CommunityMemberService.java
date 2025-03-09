package com.example.idolverse.domain.communitymember.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.idolverse.domain.community.entity.Community;
import com.example.idolverse.domain.community.service.CommunityService;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.communitymember.dto.CommunityMemberUpdateRequestDto;
import com.example.idolverse.domain.communitymember.dto.CommunityRegisterRequestDto;
import com.example.idolverse.domain.communitymember.entity.CommunityMember;
import com.example.idolverse.domain.communitymember.repository.CommunityMemberRepository;
import com.example.idolverse.domain.image.service.ImageService;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.service.MemberService;
import com.example.idolverse.global.common.entity.CustomMemberDetails;
import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityMemberService {

	private final CommunityMemberRepository communityMemberRepository;
	private final CommunityService communityService;
	private final MemberService memberService;
	private final ImageService imageService;

	public boolean existsMemberInCommunity(Community community, Member member) {
		if (communityMemberRepository.existsByCommunityAndMember(community, member)) {
			return true;
		}
		throw new GeneralException(ErrorCode.COMMUNITY_MEMBER_NOT_REGISTER);
	}

	@PreAuthorize("#requestDto.memberId == authentication.principal.memberId")	// 현재 로그인한 사용자와 register 메서드를 호출한 사용자가 동일한지 검증
	@Transactional
	public CommunityMemberInfoDto register(CommunityRegisterRequestDto requestDto) {
		Community community = communityService.findById(requestDto.communityId());
		Member member = memberService.findById(requestDto.memberId());

		CommunityMember communityMember = requestDto.toEntity(community, member, requestDto.profileName());
		CommunityMember savedCommunityMember = communityMemberRepository.save(communityMember);
		return CommunityMemberInfoDto.from(savedCommunityMember);
	}

	@Transactional
	public CommunityMemberInfoDto updateInfo(CommunityMemberUpdateRequestDto requestDto, MultipartFile file, Long memberId) {
		CommunityMember communityMember = communityMemberRepository.findById(requestDto.communityMemberId())
			.orElseThrow(() -> new GeneralException(ErrorCode.MEMBER_NOT_FOUND));
		if (communityMember.getMember().getMemberId() != memberId) {
			throw new GeneralException(ErrorCode.ACCESS_DENIED);
		}

		String profileImageUrl = imageService.uploadProfileImage(file);

		communityMember.update(requestDto.profileName(), requestDto.profileComment(), profileImageUrl);
		return CommunityMemberInfoDto.from(communityMember);
	}
}
