package com.example.idolverse.domain.account.dto;

import java.util.List;

import com.example.idolverse.domain.communitymember.dto.CommunityMemberInfoDto;
import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.entity.enums.ProfileType;

import lombok.Builder;

@Builder
public record LoginResponseDto(
	Long memberId,
	String email,
	String nickname,
	String profileName,
	ProfileType profileType,
	Boolean hasOfficialMark
) {
	public static LoginResponseDto from(Member member) {
		return LoginResponseDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.nickname(member.getNickname())
			.profileName(member.getProfileName())
			.profileType(member.getProfileType())
			.profileType(member.getProfileType())
			.hasOfficialMark(member.getHasOfficialMark())
			.build();
	}
}
