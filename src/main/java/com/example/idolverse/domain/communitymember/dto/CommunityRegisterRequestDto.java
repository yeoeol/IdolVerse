package com.example.idolverse.domain.communitymember.dto;

import com.example.idolverse.domain.member.entity.enums.ProfileType;

public record CommunityRegisterRequestDto(
	String profileName,
	ProfileType profileType
) {
}
