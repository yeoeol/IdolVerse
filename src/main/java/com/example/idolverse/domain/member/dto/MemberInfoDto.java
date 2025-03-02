package com.example.idolverse.domain.member.dto;

import com.example.idolverse.domain.member.entity.Member;
import com.example.idolverse.domain.member.entity.enums.ProfileType;

import lombok.Builder;

@Builder
public record MemberInfoDto(
	Long memberId,
	String email,
	String nickname,
	String profileName,
	ProfileType profileType,
	Boolean hasMembership,
	Boolean hasOfficialMark
) {

	public static MemberInfoDto from(Member member) {
		return MemberInfoDto.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.nickname(member.getNickname())
			.profileName(member.getProfileName())
			.profileType(member.getProfileType())
			.hasMembership(member.getHasMembership())
			.hasOfficialMark(member.getHasOfficialMark())
			.build();
	}
}
